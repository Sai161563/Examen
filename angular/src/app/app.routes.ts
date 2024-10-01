import { Routes } from '@angular/router';
import { HomeComponent } from './component/home/home.component';
import { UserLoginComponent } from './component/user-login/user-login.component';
import { UserRegistrationComponent } from './component/user-registration/user-registration.component';
import { ForgotPasswordComponent } from './component/forgot-password/forgot-password.component';
import { ExamInstructionsComponent } from './component/exam-instructions/exam-instructions.component';
import { ExamComponent } from './component/exam/exam.component';
import { ReportCardComponent } from './component/report-card/report-card.component';
import { AdminLoginComponent } from './component/admin-login/admin-login.component';
import { LoginComponent } from './component/login/login.component';
import { AboutusComponent } from './component/aboutus/aboutus.component';
import { AdminComponent } from './component/admin/admin.component';
import { ViewQuestionsComponent } from './component/view-questions/view-questions.component';
import { AddQuestionComponent } from './component/add-question/add-question.component';
import { UserProfileComponent } from './component/user-profile/user-profile.component';
import { AdminPasswordComponent } from './component/admin-password/admin-password.component';

export const routes: Routes = [
      { path: '', redirectTo:'home',pathMatch:'full'},
      { path: '', component: HomeComponent },
      { path: 'user-login', component: UserLoginComponent },
      { path: 'user-registration', component: UserRegistrationComponent },
      { path: 'forgot-password', component: ForgotPasswordComponent },
      { path: 'exam-instructions', component: ExamInstructionsComponent },
      { path: 'exam/:eid', component: ExamComponent },
      { path: 'report-card/:data', component: ReportCardComponent },
      { path: 'admin-login', component: AdminLoginComponent},
      { path: 'login', component:LoginComponent},
      { path: 'aboutus', component:AboutusComponent},
      { path: 'admin', component:AdminComponent},
      { path: 'view-questions/:eid', component:ViewQuestionsComponent},
      { path: 'add-questions', component:AddQuestionComponent},
      { path: 'profile', component:UserProfileComponent},
      { path: 'admin-password', component:AdminPasswordComponent}
];
