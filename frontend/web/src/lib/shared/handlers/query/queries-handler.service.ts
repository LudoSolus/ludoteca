import type { Axios } from 'axios';
import type { IExternalQuery } from './external-query.interface';
import type { IQueryResult } from './query-result.interface';
import type { IQuery } from './query.interface';
import { catchError, Observable, throwError } from 'rxjs';
import { authService } from '$lib/shared/stores/auth';
import { get } from 'svelte/store';
import { toast } from 'svoast';

export class QueriesHandlerService {
	constructor(public axios: Axios) {
		if (get(authService.isAuthenticated))
			axios.defaults.headers.common['Authorization'] = `Bearer ${get(authService.getUserToken())}`;
	}

	public handle<T>(query: IQuery<T>): Observable<IQueryResult<T>> {
		return query.execute(this).pipe(
			catchError((err) => {
				toast.error(err?.response?.data?.errorMessage || 'Ocorreu um erro na requisição.', {
					closable: true
				});
				return throwError(() => err);
			})
		);
	}

	public handleExternal<T>(query: IExternalQuery<T>): Observable<T> {
		return query.execute(this).pipe(
			catchError((err) => {
				toast.error(err?.response?.data?.errorMessage || 'Ocorreu um erro na requisição.', {
					closable: true
				});
				return throwError(() => err);
			})
		);
	}
}

