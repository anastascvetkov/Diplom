import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ErrorStatus } from 'src/app/_model/error-status.enum';

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.css']
})
export class ErrorComponent implements OnInit {
  @Input() data?: ErrorStatus;

  constructor(public activeModal: NgbActiveModal) {}

  ngOnInit(): void {}

}
