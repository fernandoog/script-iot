import {Component, OnInit} from '@angular/core';
import {ScriptService} from '../script.service';
import {Script} from '../script';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-update-script',
  templateUrl: './update-script.component.html',
  styleUrls: ['./update-script.component.css']
})
export class UpdateScriptComponent implements OnInit {

  id: number;
  script: Script = new Script();

  constructor(private scriptService: ScriptService,
              private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];

    this.scriptService.getScriptById(this.id).subscribe(data => {
      this.script = data;
    }, error => console.log(error));
  }

  onSubmit() {
    this.scriptService.updateScript(this.id, this.script).subscribe(data => {
        this.goToScriptList();
      }
      , error => console.log(error));
  }

  goToScriptList() {
    this.router.navigate(['/scripts']);
  }
}
