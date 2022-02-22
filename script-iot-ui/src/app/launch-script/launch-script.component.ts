import {Component, OnInit} from '@angular/core';
import {Script} from '../script';
import {ActivatedRoute} from '@angular/router';
import {ScriptService} from '../script.service';

@Component({
  selector: 'app-launch-script',
  templateUrl: './launch-script.component.html',
  styleUrls: ['./launch-script.component.css']
})
export class LaunchScriptComponent implements OnInit {

  id: number
  script: Script
  result: string

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
