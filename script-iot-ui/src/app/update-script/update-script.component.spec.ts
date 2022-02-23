import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {UpdateScriptComponent} from './update-script.component';

describe('UpdateScriptComponent', () => {
  let component: UpdateScriptComponent;
  let fixture: ComponentFixture<UpdateScriptComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [UpdateScriptComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateScriptComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
