import {AfterViewInit, Component, Input, OnInit, ViewChild} from '@angular/core';
import {MatPaginator, MatSort, MatTableDataSource} from '@angular/material';
import {ColumnMap, TableSettings} from "./layout.model";

@Component({
  selector: 'data-table',
  templateUrl: './data-table.component.html',
  styleUrls: ['./data-table.component.css'],
})
export class DataTableComponent implements OnInit, AfterViewInit {
  @Input() records: any;
  @Input() caption: string;
  @Input() settings: TableSettings[];

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  dataSource = new MatTableDataSource([]);

  /* names of for-loop columns */
  colNames = [];

  /* names of for-loop columns + manually added static columns */
  columnsToDisplay = [];

  columnsMap: ColumnMap[];
  primaryKeys = [];

  ngOnInit() {
    this.dataSource = new MatTableDataSource(this.records);
    this.primaryKeys = this.settings.map(x => x.primaryKey);

    this.setColumnsNames();

    this.addStaticColumns();

    console.log(this.columnsMap);
  }

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }

  setColumnsNames() {

    if (this.settings) {
      this.columnsMap = this.settings
        .map(col => new ColumnMap(col));
    } else {
      this.columnsMap = Object.keys(this.records[0]).map(key => {
        return new ColumnMap({primaryKey: key});
      });
    }
    for (let map of this.columnsMap) {
      this.colNames.push(map.header);
    }
  }

  addStaticColumns() {
    this.columnsToDisplay = this.primaryKeys.concat('edit');
  }

  public doFilter = (value: string) => {
    this.dataSource.filter = value.trim().toLocaleLowerCase();
  };

  public edit() {
  };

  public save() {
  };

  public cancel() {
  };

}
