import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule } from '@angular/forms';
import { HomeModule } from './features/home/home.module';
import { ContactModule } from './features/contact/contact.module';
import { ProductsModule } from './features/products/products.module';
import { HeaderComponent } from './features/home/components/header/header.component';
import { FooterComponent } from './features/home/components/footer/footer.component';
import { AppComponent } from './app.component';
import { RouterModule } from '@angular/router';
import { RouterOutlet } from '@angular/router';
import { routes } from './app.routes';

import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { authInterceptor } from './core/interceptors/auth.interceptor';

@NgModule({
  declarations: [
    AppComponent,
    FooterComponent,
  ],
  imports: [
    MatButtonModule,
    ReactiveFormsModule,
    CommonModule,
    BrowserModule,
    BrowserAnimationsModule,
    RouterModule,
    RouterModule.forRoot(routes),
    HomeModule,
    ContactModule,
    ProductsModule,
    HeaderComponent,  
    FooterComponent,    
    RouterOutlet      
  ],
  providers: [
    provideHttpClient(withInterceptors([authInterceptor]))
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
