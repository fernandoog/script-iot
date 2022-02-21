import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http'
import {Observable} from 'rxjs';
import {Script} from './script';

@Injectable({
  providedIn: 'root'
})
export class ScriptService {

  private baseURL = "http://localhost:8080/api/v1/scripts";

  constructor(private httpClient: HttpClient) {
  }

  getScriptsList(): Observable<Script[]> {
    return this.httpClient.get<Script[]>(`${this.baseURL}`);
  }

  createScript(script: Script): Observable<Object> {
    return this.httpClient.post(`${this.baseURL}`, script);
  }

  getScriptById(id: number): Observable<Script> {
    return this.httpClient.get<Script>(`${this.baseURL}/${id}`);
  }

  updateScript(id: number, script: Script): Observable<Object> {
    return this.httpClient.put(`${this.baseURL}/${id}`, script);
  }

  deleteScript(id: number): Observable<Object> {
    return this.httpClient.delete(`${this.baseURL}/${id}`);
  }
}
