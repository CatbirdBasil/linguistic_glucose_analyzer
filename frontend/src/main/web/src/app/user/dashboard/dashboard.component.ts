import {Component} from '@angular/core';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {

  //BAR CHART
  public bar: string = 'bar';

  public barDatasets: Array<any> = [
    {data: [65, 59, 80, 81, 56, 55, 40], label: 'My First dataset'}
  ];

  public barLabels: Array<any> = ['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange'];

  public barColors: Array<any> = [
    {
      backgroundColor: [
        'rgba(255, 99, 132, 0.2)',
        'rgba(54, 162, 235, 0.2)',
        'rgba(255, 206, 86, 0.2)',
        'rgba(75, 192, 192, 0.2)',
        'rgba(153, 102, 255, 0.2)',
        'rgba(255, 159, 64, 0.2)'
      ],
      borderColor: [
        'rgba(255,99,132,1)',
        'rgba(54, 162, 235, 1)',
        'rgba(255, 206, 86, 1)',
        'rgba(75, 192, 192, 1)',
        'rgba(153, 102, 255, 1)',
        'rgba(255, 159, 64, 1)'
      ],
      borderWidth: 2,
    }
  ];

  public barOptions: any = {
    responsive: true
  };
  //----------------

  //DOUGHNUT CHART
  public doughnut: string = 'doughnut';

  public doughnutDatasets: Array<any> = [
    {data: [300, 50, 100, 40, 120], label: 'My First dataset'}
  ];

  public doughnutLabels: Array<any> = ['Red', 'Green', 'Yellow', 'Grey', 'Dark Grey'];

  public doughnutColors: Array<any> = [
    {
      backgroundColor: ['#F7464A', '#46BFBD', '#FDB45C', '#949FB1', '#4D5360'],
      hoverBackgroundColor: ['#FF5A5E', '#5AD3D1', '#FFC870', '#A8B3C5', '#616774'],
      borderWidth: 2,
    }
  ];

  public doughnutOptions: any = {
    responsive: true
  };
  //----------------

  //LINE CHART
  public line: string = 'line';

  public lineDatasets: Array<any> = [
    {data: [65, 59, 80, 81, 56, 55, 40], label: 'My First dataset'},
    {data: [28, 48, 40, 19, 86, 27, 90], label: 'My Second dataset'}
  ];

  public lineLabels: Array<any> = ['January', 'February', 'March', 'April', 'May', 'June', 'July'];

  public lineColors: Array<any> = [
    {
      backgroundColor: 'rgba(105, 0, 132, .2)',
      borderColor: 'rgba(200, 99, 132, .7)',
      borderWidth: 2,
    },
    {
      backgroundColor: 'rgba(0, 137, 132, .2)',
      borderColor: 'rgba(0, 10, 130, .7)',
      borderWidth: 2,
    }
  ];

  public lineOptions: any = {
    responsive: true
  };
  //----------------

  //Events
  public barClicked(e: any): void {
  }

  public barHovered(e: any): void {
  }

  public doughnutClicked(e: any): void {
  }

  public doughnutHovered(e: any): void {
  }

  public lineClicked(e: any): void {
  }

  public lineHovered(e: any): void {
  }

}
