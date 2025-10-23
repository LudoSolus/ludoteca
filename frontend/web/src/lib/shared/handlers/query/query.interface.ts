import { Observable } from 'rxjs';
import { QueriesHandlerService } from './queries-handler.service';
import type { IQueryResult } from './query-result.interface';

export interface IQuery<T> {
  execute(queriesHandler: QueriesHandlerService): Observable<IQueryResult<T>>;
}
