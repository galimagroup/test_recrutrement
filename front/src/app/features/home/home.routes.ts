
import { Routes } from '@angular/router';
import { HomeComponent } from './home.component';

import { AuthGuard } from '../../core/services/auth.guard';

export const HOME_ROUTES: Routes = [
  {
    path: '',
    component: HomeComponent,
    canActivate: [AuthGuard]
  }
];
