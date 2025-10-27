import type { IExternalQuery } from "./external-query.interface";
import type { IQueryResult } from "./query-result.interface";
import type { IQuery } from "./query.interface";
import { catchError, Observable, throwError } from 'rxjs';

export class QueriesHandlerService {

  constructor(
    // public http: HttpClient,
  ) { }

  public handle<T>(query: IQuery<T>): Observable<IQueryResult<T>> {
    return query.execute(this).pipe(
      catchError(err => {
        const errorMessage = err?.error?.errorMessage || 'Ocorreu um erro na requisição.';
        return throwError(() => err);
      }),
    );
  }

  public handleExternal<T>(query: IExternalQuery<T>): Observable<T> {
    return query.execute(this).pipe(
      catchError(err => {
        return throwError(() => err);
      }),
    );
  }
}