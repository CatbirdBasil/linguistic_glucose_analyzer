import {Component, OnInit, SimpleChanges, TemplateRef, ViewChild, ViewEncapsulation} from '@angular/core';
import {GlucoseService} from "../service/glucose-service";
import {GlucoseDataRecord} from "@models/glucose-data-record";

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

  currPage: any = {};

  @ViewChild('fineGlucoseInfo') fineGlucoseInfoTemplate: TemplateRef<any>;
  @ViewChild('hyperRisk') hyperRiskTemplate: TemplateRef<any>;
  @ViewChild('hyperState') hyperStateTemplate: TemplateRef<any>;
  @ViewChild('hypoRisk') hypoRiskTemplate: TemplateRef<any>;
  @ViewChild('hypoState') hypoStateTemplate: TemplateRef<any>;

  @ViewChild('regularRow') regularRowTemplate: TemplateRef<any>;
  @ViewChild('editableRow') editableRowTemplate: TemplateRef<any>;

  constructor(private glucoseService: GlucoseService) {
  }

  ngOnInit() {
    this.isLoading = true;

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
      }
    )
  }

  onChangePage(onPageRecords: Array<any>) {
    this.onPageRecords = onPageRecords;
  }

  loadInfoTemplate(record: GlucoseDataRecord) {
    let val = record.value;
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
          let index = this.glucoseRecords.findIndex((x) => x.id == this.editedRecord.id);
          this.glucoseRecords[index] = data;

          index = this.onPageRecords.findIndex((x) => x.id == this.editedRecord.id);
          this.onPageRecords[index] = data;
          this.onPageRecords.pop();

          console.log("SAVED: ", this.editedRecord);
          this.isNewRecord = false;
          this.editedRecord = null;

          this.onPageRecords.sort((a, b) => b.eventTime.getTime() - a.eventTime.getTime());
          this.glucoseRecords.sort((a, b) => b.eventTime.getTime() - a.eventTime.getTime());
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
        },
        error => console.log(error)
      )
    }
  }

  prepareForDeletion(record: GlucoseDataRecord) {
    this.recordToDelete = record;
  }
}
