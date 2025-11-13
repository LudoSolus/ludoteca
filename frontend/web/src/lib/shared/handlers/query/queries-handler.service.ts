import type { Axios } from 'axios';
import type { IExternalQuery } from './external-query.interface';
import type { IQueryResult } from './query-result.interface';
import type { IQuery } from './query.interface';
import { catchError, Observable, throwError } from 'rxjs';
import { authService } from '$lib/shared/stores/auth';
import { get } from 'svelte/store';

export class QueriesHandlerService {
	constructor(public axios: Axios) {
		const token = get(authService.getUserToken());
		if (token) axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
	}

	public handle<T>(query: IQuery<T>): Observable<IQueryResult<T>> {
		return query.execute(this).pipe(
			catchError((err) => {
				const errorMessage = err?.error?.errorMessage || 'Ocorreu um erro na requisição.';
				return throwError(() => err);
			})
		);
	}

	public handleExternal<T>(query: IExternalQuery<T>): Observable<T> {
		return query.execute(this).pipe(
			catchError((err) => {
				return throwError(() => err);
			})
		);
	}
}
