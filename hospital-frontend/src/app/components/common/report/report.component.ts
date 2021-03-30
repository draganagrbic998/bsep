import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Report } from 'src/app/models/report';
import { ReportService } from 'src/app/services/report.service';

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
  searchForm: FormGroup = new FormGroup({
    start: new FormControl(''),
    end: new FormControl('')
  });

  getReport(): void{
    this.fetchPending = true;
    // tslint:disable-next-line: deprecation
    this.reportService.report(this.searchForm.value).subscribe(
      (report: Report) => {
        this.fetchPending = false;
        this.report = report;
      }
    );
  }

  ngOnInit(): void {
    this.getReport();
  }

}
