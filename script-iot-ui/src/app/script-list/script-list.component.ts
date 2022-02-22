import {Component, OnInit} from '@angular/core';
import {Script} from '../script'
import {ScriptService} from '../script.service'
import {Router} from '@angular/router';

@Component({
  selector: 'app-script-list',
  templateUrl: './script-list.component.html',
  styleUrls: ['./script-list.component.css']
})
export class ScriptListComponent implements OnInit {

  scripts: Script[];

  constructor(private scriptService: ScriptService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.getScripts();
  }

  scriptDetails(id: number) {
    this.router.navigate(['script-details', id]);
  }

  launchScript(id: number) {
    this.router.navigate(['launch-script', id]);
  }

  updateScript(id: number) {
    this.router.navigate(['update-script', id]);
  }

  deleteScript(id: number) {
    this.scriptService.deleteScript(id).subscribe(data => {
      console.log(data);
      this.getScripts();
    })
  }

  private getScripts() {
    this.scriptService.getScriptsList().subscribe(data => {
      this.scripts = data;
    });
  }
}
