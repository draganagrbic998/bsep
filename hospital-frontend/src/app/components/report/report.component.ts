import { Component, OnInit } from '@angular/core';
import { Report } from 'src/app/models/report';
import { ReportService } from 'src/app/services/report/report.service';

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.scss']
})
export class ReportComponent implements OnInit {

  constructor(
    private reportService: ReportService
  ) { }

  report: Report;
  fetchPending = true;

  ngOnInit(): void {
    // tslint:disable-next-line: deprecation
    this.reportService.report().subscribe(
      (report: Report) => {
        this.fetchPending = false;
        this.report = report;
      }
    );
  }

}
