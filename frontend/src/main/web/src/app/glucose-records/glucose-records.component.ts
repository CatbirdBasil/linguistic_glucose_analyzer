import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {GlucoseService} from "../service/glucose-service";

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

  public currPage;

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
}
