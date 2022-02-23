import {Component, OnInit} from '@angular/core';
import {Script} from '../script';
import {ActivatedRoute} from '@angular/router';
import {ScriptService} from '../script.service';

@Component({
  selector: 'app-script-details',
  templateUrl: './script-details.component.html',
  styleUrls: ['./script-details.component.css']
})
export class ScriptDetailsComponent implements OnInit {

  id: number
  script: Script

  constructor(private route: ActivatedRoute, private scriptService: ScriptService) {
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];

    this.script = new Script();
    this.scriptService.getScriptById(this.id).subscribe(data => {
      this.script = data;
    });
  }

}
