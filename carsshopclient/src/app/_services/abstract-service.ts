import { HttpHeaders } from "@angular/common/http";
import { Observable, of } from "rxjs";

export abstract class AbstractService {
  httpOptions = {
    headers: new HttpHeaders({ 
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin': 'http://localhost:4200' 
    })
  };

  handleError<T>(_operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    };
  }
}