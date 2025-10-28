import { Observable } from 'rxjs';
import { QueriesHandlerService } from './queries-handler.service';

export interface IExternalQuery<T> {
  execute(queriesHandler: QueriesHandlerService): Observable<T>;
}
