import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthenticationService } from '../../service/authentication.service';
import { ExamService } from '../../service/exam.service';
import { CommonModule } from '@angular/common';
import { AdminService } from '../../service/admin.service';
import { FormsModule } from '@angular/forms';
import jsPDF from 'jspdf';
import html2canvas from 'html2canvas';

interface ReportDTO {
  reportId: number;
  totalMarks: number;
  fname: string;
  examName: string;
}

@Component({
  selector: 'app-report-card',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './report-card.component.html',
  styleUrl: './report-card.component.css'
})
export class ReportCardComponent implements OnInit {
  report!: ReportDTO;
  report1!: any[];
  report2!: any[];

  filteredReport: any[] = [];
  examNames: string[] = [];
  states: string[] = [];
  cities: string[] = [];
  marksRanges: string[] = ['0-5', '6-7', '8-9', '10-10'];
  
  filters = {
    examName: '',
    state: '',
    city: '',
    marksRange: ''
  };

  constructor(private route: ActivatedRoute,
    private router: Router,
    private examService: ExamService,
    private adminService: AdminService,
    private authService: AuthenticationService) {
      if(!this.authService.isLoggedIn()) {
        alert("You don't have access");
        this.router.navigate(['/']);
      }
    };

  ngOnInit(): void {
    const obj: string | any = this.route.snapshot.paramMap.get('data');
    if(obj != 'all' && obj != "any" && this.authService.isLoggedIn()) {
      console.log(obj);
      this.report = JSON.parse(obj);
      console.log(this.report);
    } else if(obj != 'any' && this.authService.isLoggedIn()) {
      this.examService.getReport().subscribe((data) => {
        this.report1 = data;
        console.log(this.report1);
      });
    } else if(this.authService.isAdminLoggedIn()) {
      this.adminService.getReports().subscribe((data) => {
        this.report2 = data;
        console.log(this.report2);
        this.filteredReport = this.report2;
        this.extractFilterOptions();
      })

    }
  }

  generatePDF(): void {
    const reportElement = document.getElementById('report-content');
    if (reportElement) {
      html2canvas(reportElement).then((canvas) => {
        const imgData = canvas.toDataURL('image/png');
        const pdf = new jsPDF();
        const imgProps = pdf.getImageProperties(imgData);
        const pdfWidth = pdf.internal.pageSize.getWidth();
        const pdfHeight = (imgProps.height * pdfWidth) / imgProps.width;
        pdf.addImage(imgData, 'PNG', 0, 0, pdfWidth, pdfHeight);
        pdf.save(`report_${this.report.reportId}.pdf`);
      });
    }
  }


  extractFilterOptions(): void {
    this.examNames = [...new Set(this.report2.map(result => result.examName))];
    this.states = [...new Set(this.report2.map(result => result.state))];
    this.cities = [...new Set(this.report2.map(result => result.city))];
  }

  applyFilters(): void {
    this.filteredReport = this.report2.filter(result => {
      return (
        (this.filters.examName === '' || result.examName === this.filters.examName) &&
        (this.filters.state === '' || result.state === this.filters.state) &&
        (this.filters.city === '' || result.city === this.filters.city) &&
        this.isInMarksRange(result.totalMarks)
      );
    });
  }

  isInMarksRange(marks: number): boolean {
    if (this.filters.marksRange === '') return true;
    const [min, max] = this.filters.marksRange.split('-').map(Number);
    return marks >= min && marks <= max;
  }

  resetFilters(): void {
    this.filters = {
      examName: '',
      state: '',
      city: '',
      marksRange: ''
    };
    this.filteredReport = this.report2;
  }

}
