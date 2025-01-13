import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LoginsignRoutingModule } from './loginsign-routing.module';
import { LoginComponent } from './login/login.component';
import { SignComponent } from './sign/sign.component';
import { ReactiveFormsModule } from '@angular/forms';
import { ToastrModule } from 'ngx-toastr';

@NgModule({
  declarations: [
    LoginComponent,
    SignComponent
  ],
  imports: [
    CommonModule,

    LoginsignRoutingModule,
    ReactiveFormsModule,
  ],
})

export class LoginsignModule { }
