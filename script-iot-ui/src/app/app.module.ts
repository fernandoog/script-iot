import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http'
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {ScriptListComponent} from './script-list/script-list.component';
import {CreateScriptComponent} from './create-script/create-script.component';
import {FormsModule} from '@angular/forms';
import {UpdateScriptComponent} from './update-script/update-script.component';
import {ScriptDetailsComponent} from './script-details/script-details.component'
import {LaunchScriptComponent} from './launch-script/launch-script.component'

@NgModule({
  declarations: [
    AppComponent,
    ScriptListComponent,
    CreateScriptComponent,
    UpdateScriptComponent,
    ScriptDetailsComponent,
    LaunchScriptComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
