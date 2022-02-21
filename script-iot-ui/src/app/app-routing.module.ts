import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ScriptListComponent} from './script-list/script-list.component';
import {CreateScriptComponent} from './create-script/create-script.component';
import {UpdateScriptComponent} from './update-script/update-script.component';
import {ScriptDetailsComponent} from './script-details/script-details.component';

const routes: Routes = [
  {path: 'scripts', component: ScriptListComponent},
  {path: 'create-script', component: CreateScriptComponent},
  {path: '', redirectTo: 'scripts', pathMatch: 'full'},
  {path: 'update-script/:id', component: UpdateScriptComponent},
  {path: 'script-details/:id', component: ScriptDetailsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
