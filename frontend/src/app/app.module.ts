import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ActivitiesComponent } from './activities/activities.component';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { HomeComponent } from './pages/home/home.component';
import { MaterialToysComponent } from './material-toys/material-toys.component';
import { MilestonesComponent } from './milestones/milestones.component';
import { ParentsComponent } from './parents/parents.component';
import { SkillsComponent } from './skills/skills.component';
import { UsersComponent } from './admin-panel/pages/users/users.component';
import { MaterialModule } from 'src/material.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { ToastrModule } from 'ngx-toastr';
import { AdminPanelComponent } from './admin-panel/admin-panel.component';
import { DashboardComponent } from './admin-panel/pages/dashboard/dashboard.component';
import { ProductsComponent } from './admin-panel/pages/products/products.component';
import { SubscriptionsComponent } from './admin-panel/pages/subscriptions/subscriptions.component';
import { ConfirmDialogComponent } from './admin-panel/pages/users/confirm-dialog/confirm-dialog.component';
import {
  MAT_SNACK_BAR_DEFAULT_OPTIONS,
  MatSnackBarModule,
} from "@angular/material/snack-bar";
import { CreateUserComponent } from './admin-panel/pages/users/create-user/create-user.component';
import { MatIconModule } from "@angular/material/icon";
import { CashInComponent } from './admin-panel/pages/cash-in/cash-in.component';
import { HeaderComponent } from './shared/header/header.component';
import { NavbarComponent } from './shared/navbar/navbar.component';
import { FooterComponent } from './shared/footer/footer.component';
import { BlogComponent } from './pages/blog/blog.component';
import { AboutComponent } from './pages/about/about.component';
import { LandingPageComponent } from './pages/landing-page/landing-page.component';
import { AppFeaturesComponent } from './pages/app-features/app-features.component';
import { CounterComponent } from './pages/counter/counter.component';
import { ContactComponent } from './pages/contact/contact.component';
import { AppPageComponent } from './pages/app-page/app-page.component';
import { AuthComponent } from './auth/auth.component';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    ActivitiesComponent,
    LoginComponent,
    RegisterComponent,
    MaterialToysComponent,
    MilestonesComponent,
    ParentsComponent,
    SkillsComponent,
    UsersComponent,
    AdminPanelComponent,
    DashboardComponent,
    ProductsComponent,
    SubscriptionsComponent,
    ConfirmDialogComponent,
    CreateUserComponent,
    CashInComponent,
    HeaderComponent,
    NavbarComponent,
    FooterComponent,
    BlogComponent,
    AboutComponent,
    LandingPageComponent,
    AppFeaturesComponent,
    CounterComponent,
    ContactComponent,
    AppPageComponent,
    AuthComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    MatSnackBarModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatIconModule,
    ToastrModule.forRoot(),
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
