import {Component, ElementRef, OnInit, SimpleChanges, TemplateRef, ViewChild, ViewEncapsulation} from '@angular/core';
import {GlucoseService} from "../service/glucose-service";
import {GlucoseDataRecord} from "@models/glucose-data-record";
import {PossibleGlucoseValue} from "@models/possible-glucose-value";
import {Chart, ChartConfiguration} from 'chart.js'
import {formatDate} from "@angular/common";
import {PredictionService} from "../service/prediction-service";

@Component({
  selector: 'app-glucose-records',
  templateUrl: './glucose-records.component.html',
  encapsulation: ViewEncapsulation.None,
  styleUrls: ['./glucose-records.component.scss']
})
export class GlucoseRecordsComponent implements OnInit {

  public glucoseRecords = [];
  public onPageRecords = [];
  public isLoading = true;

  editedRecord: GlucoseDataRecord;
  recordToDelete: GlucoseDataRecord;
  isNewRecord: boolean;

  loadingPrediction: boolean;

  possibleValue: PossibleGlucoseValue;

  linechart: Chart;
  linechartConfig: ChartConfiguration;

  receivedGlucose = {};

  @ViewChild('fineGlucoseInfo') fineGlucoseInfoTemplate: TemplateRef<any>;
  @ViewChild('hyperRisk') hyperRiskTemplate: TemplateRef<any>;
  @ViewChild('hyperState') hyperStateTemplate: TemplateRef<any>;
  @ViewChild('hypoRisk') hypoRiskTemplate: TemplateRef<any>;
  @ViewChild('hypoState') hypoStateTemplate: TemplateRef<any>;

  @ViewChild('regularRow') regularRowTemplate: TemplateRef<any>;
  @ViewChild('editableRow') editableRowTemplate: TemplateRef<any>;

  constructor(private glucoseService: GlucoseService,
              private predictionService: PredictionService) {
  }

  ngOnInit() {
    this.isLoading = true;
    this.loadingPrediction = false;

    this.glucoseService.getGlucoseRecordsForCurrentUser().subscribe(
      data => {
        console.log(data);
        console.log("^Records");
        data.forEach((d) => {
          d.eventTime = new Date(d.eventTime);
        });
        data.sort((a, b) => b.eventTime.getTime() - a.eventTime.getTime())
        this.glucoseRecords = data;
        this.isLoading = false;

        this.initChart();
      }
    )

    this.loadPrediction();
    //
    // this.possibleValue = new class implements PossibleGlucoseValue {
    //   approximateTime: Date;
    //   max: number;
    //   min: number;
    // }
    //
    // this.possibleValue.approximateTime = new Date(Date.now());
    // this.possibleValue.min = 212.123521;
    // this.possibleValue.max = 225.1231;
  }

  onChangePage(onPageRecords: Array<any>) {
    this.onPageRecords = onPageRecords;
  }

  loadInfoTemplate(record: GlucoseDataRecord) {
    return this.loadInfoTemplateByValue(record.value);
  }

  loadInfoTemplateByValue(val: number) {
    if (val <= 70) {
      return this.hypoStateTemplate;
    } else if (val <= 80) {
      return this.hypoRiskTemplate;
    } else if (val >= 200) {
      return this.hyperStateTemplate;
    } else if (val >= 180) {
      return this.hyperRiskTemplate;
    } else {
      return this.fineGlucoseInfoTemplate;
    }
  }

  loadInfoTemplateByValueForPrediction(possibleValue: PossibleGlucoseValue) {
    let minTemplate = this.loadInfoTemplateByValue(possibleValue.min);

    if (minTemplate == this.hypoStateTemplate || this.hypoRiskTemplate) {
      return minTemplate;
    }

    return this.loadInfoTemplateByValue(possibleValue.max);
  }

  loadRecordTemplate(record: GlucoseDataRecord) {
    if (this.editedRecord && this.editedRecord.id === record.id) {
      return this.editableRowTemplate;
    } else {
      return this.regularRowTemplate;
    }
  }

  // добавление пользователя
  addRecord() {
    this.editedRecord = new class implements GlucoseDataRecord {
      eventTime: Date;
      id: number;
      value: number;
    }();

    this.editedRecord.id = 0;
    this.editedRecord.eventTime = new Date(Date.now());
    this.editedRecord.value = 0;

    this.glucoseRecords.unshift(this.editedRecord);
    this.onPageRecords.unshift(this.editedRecord);
    this.isNewRecord = true;
  }

  // редактирование пользователя
  editRecord(record: GlucoseDataRecord) {
    this.editedRecord = new class implements GlucoseDataRecord {
      eventTime: Date;
      id: number;
      value: number;
    }();

    this.editedRecord.id = record.id;
    this.editedRecord.eventTime = record.eventTime;
    this.editedRecord.value = record.value;
  }

  // сохраняем пользователя
  saveRecord() {
    if (this.isNewRecord) {
      // добавляем пользователя
      // this..createUser(this.editedUser).subscribe(data => {
      //   this.statusMessage = 'Данные успешно добавлены',
      //     this.loadUsers();
      // });
      this.editedRecord.id = null;

      this.glucoseService.saveGlucoseRecord(this.editedRecord).subscribe(
        data => {
          data.eventTime = new Date(data.eventTime);
          let index = this.glucoseRecords.findIndex((x) => x.id == this.editedRecord.id);
          this.glucoseRecords[index] = data;

          index = this.onPageRecords.findIndex((x) => x.id == this.editedRecord.id);
          this.onPageRecords[index] = data;

          if (this.onPageRecords.length > 20) {
            this.onPageRecords.pop();
          }

          console.log("SAVED: ", this.editedRecord);
          this.isNewRecord = false;
          this.editedRecord = null;

          this.onPageRecords.sort((a, b) => b.eventTime.getTime() - a.eventTime.getTime());
          this.glucoseRecords.sort((a, b) => b.eventTime.getTime() - a.eventTime.getTime());
          this.updateChartData();
        },
        err => {
          console.log(err);
          this.isNewRecord = false;
          this.editedRecord = null;
        }
      )
    } else {
      this.glucoseService.updateGlucoseRecord(this.editedRecord).subscribe(
        data => {
          let index = this.glucoseRecords.findIndex((x) => x.id == this.editedRecord.id);
          this.glucoseRecords[index] = this.editedRecord;

          index = this.onPageRecords.findIndex((x) => x.id == this.editedRecord.id);
          this.onPageRecords[index] = this.editedRecord;

          console.log("EDITED: ", this.editedRecord);
          this.editedRecord = null;

          this.onPageRecords.sort((a, b) => b.eventTime.getTime() - a.eventTime.getTime());
          this.glucoseRecords.sort((a, b) => b.eventTime.getTime() - a.eventTime.getTime());
          this.updateChartData();
        },
        err => {
          console.log(err);
          this.editedRecord = null;
        }
      )
    }
  }

  // отмена редактирования
  cancel() {
    // если отмена при добавлении, удаляем последнюю запись
    if (this.isNewRecord) {
      this.glucoseRecords.splice(0, 1);
      this.onPageRecords.splice(0, 1);
      this.isNewRecord = false;
    }

    this.editedRecord = null;
  }

  // удаление пользователя
  deleteRecord() {
    console.log("DELETED: ", this.recordToDelete);

    if (this.recordToDelete.id != null && this.recordToDelete.id != 0) {
      this.glucoseService.deleteGlucoseRecord(this.recordToDelete.id).subscribe(
        () => {
          let index = this.glucoseRecords.findIndex((x) => x.id == this.recordToDelete.id);
          this.glucoseRecords.splice(index, 1);

          index = this.onPageRecords.findIndex((x) => x.id == this.recordToDelete.id);
          this.onPageRecords.splice(index, 1);
          this.updateChartData();
        },
        error => console.log(error)
      )
    }
  }

  prepareForDeletion(record: GlucoseDataRecord) {
    this.recordToDelete = record;
  }

  loadPrediction() {
    this.loadingPrediction = true;

    this.predictionService.getPossibleFutureGlucoseValue().subscribe(
      data => {
        data.approximateTime = new Date(data.approximateTime);
        this.possibleValue = data;
        this.loadingPrediction = false;
      },
      error => console.log(error)
    )
  }

  @ViewChild('myCanvas') myCanvas: ElementRef;
  public context: CanvasRenderingContext2D;

  // initChart() {
  //
  //   let chartRecords = this.glucoseRecords.slice(0, 2016);
  //   chartRecords = chartRecords.filter((val, i) => i % 12 == 0);
  //
  //   this.context = (<HTMLCanvasElement>this.myCanvas.nativeElement).getContext('2d');
  //   this.linechart = new Chart(this.context, {
  //       type: 'line',
  //       data: {
  //         labels: chartRecords
  //           .map(x => x.eventTime)
  //         // .map(x => formatDate(x, 'EEEE dd/MM/yyyy', 'en-US') + ' at ' + formatDate(x, 'HH:mm:ss', 'en-US')),
  //         ,
  //         datasets: [{
  //           data: chartRecords.map(x => x.value),
  //           borderColor: '#c45858',
  //           backgroundColor: '#fd9899',
  //           fill: false
  //         }]
  //       },
  //       options: {
  //         title: {
  //           display: true,
  //           text: 'Glucose measures for last week',
  //           fontSize: 20
  //         },
  //         legend: {
  //           display: false
  //         },
  //         scales: {
  //           xAxes: [{
  //             display: true,
  //             type: 'time',
  //             time: {
  //               unit: 'day',
  //               displayFormats: {
  //                 hour: 'HH'
  //               },
  //               stepSize: 1
  //             },
  //             distribution: 'series'
  //           }],
  //           yAxes: [{
  //             display: true,
  //             ticks: {
  //               min: 0,
  //               max: 520,
  //
  //               // forces step size to be 5 units
  //               stepSize: 10
  //             }
  //           }],
  //         },
  //         responsive: true,
  //         tooltips: {
  //           position: 'nearest'
  //         },
  //         hover: {
  //           mode: 'nearest',
  //           intersect: true
  //         },
  //       }
  //     }
  //   )
  // }

  initChart() {
    let chartRecords;

    if (this.glucoseRecords.length > 2016) {
      chartRecords = this.glucoseRecords.slice(0, 2016);
      chartRecords = chartRecords.filter((val, i) => i % 12 == 0);
    } else {
      chartRecords = this.glucoseRecords;
      let fluidMultiplier = chartRecords.length / 168;
      fluidMultiplier = Math.round(fluidMultiplier);
      if (fluidMultiplier > 1) {
        chartRecords = chartRecords.filter((val, i) => i % fluidMultiplier == 0);
      }
    }

    this.context = (<HTMLCanvasElement>this.myCanvas.nativeElement).getContext('2d');
    this.linechartConfig = {
      type: 'line',
      data: {
        labels: chartRecords
          .map(x => x.eventTime)
        // .map(x => formatDate(x, 'EEEE dd/MM/yyyy', 'en-US') + ' at ' + formatDate(x, 'HH:mm:ss', 'en-US')),
        ,
        datasets: [{
          data: chartRecords.map(x => x.value),
          borderColor: '#c45858',
          backgroundColor: '#fd9899',
          fill: false
        }]
      },
      options: {
        title: {
          display: true,
          text: 'Glucose measures for last week',
          fontSize: 20
        },
        legend: {
          display: false
        },
        scales: {
          xAxes: [{
            display: true,
            type: 'time',
            time: {
              unit: 'day',
              displayFormats: {
                hour: 'HH'
              },
              stepSize: 1
            },
            distribution: 'series'
          }],
          yAxes: [{
            display: true,
            ticks: {
              min: 0,
              max: 520,

              // forces step size to be 5 units
              stepSize: 10
            }
          }],
        },
        responsive: true,
        tooltips: {
          position: 'nearest'
        },
        hover: {
          mode: 'nearest',
          intersect: true
        },
      }
    }
    this.linechart = new Chart(this.context, this.linechartConfig);
  }

  updateChartData() {
    let chartRecords;

    if (this.glucoseRecords.length > 2016) {
      chartRecords = this.glucoseRecords.slice(0, 2016);
      chartRecords = chartRecords.filter((val, i) => i % 12 == 0);
    } else {
      chartRecords = this.glucoseRecords;
      let fluidMultiplier = chartRecords.length / 168;
      fluidMultiplier = Math.round(fluidMultiplier);
      if (fluidMultiplier > 1) {
        chartRecords = chartRecords.filter((val, i) => i % fluidMultiplier == 0);
      }
    }

    this.linechartConfig.data.datasets.forEach(function (dataset) {
      dataset.data = chartRecords.map(x => x.value);
    });

    this.linechartConfig.data.labels = chartRecords
      .map(x => x.eventTime);

    this.linechart.update();
  }
}
