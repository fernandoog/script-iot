import {Component, OnInit} from '@angular/core';
import {Script} from '../script';
import {ScriptService} from '../script.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-create-script',
  templateUrl: './create-script.component.html',
  styleUrls: ['./create-script.component.css']
})
export class CreateScriptComponent implements OnInit {

  script: Script = new Script();

  constructor(private scriptService: ScriptService,
              private router: Router) {
  }

  ngOnInit(): void {
  }

  saveScript() {
    this.scriptService.createScript(this.script).subscribe(data => {
        console.log(data);
        this.goToScriptList();
      },
      error => console.log(error));
  }

  goToScriptList() {
    this.router.navigate(['/scripts']);
  }

  onSubmit() {
    console.log(this.script);
    this.saveScript();
  }
}
