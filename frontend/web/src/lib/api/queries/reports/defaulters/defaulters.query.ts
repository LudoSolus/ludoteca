import { PUBLIC_API_URL } from '$env/static/public';
import { EErrorCode } from '$lib/shared/enums/error-code.enum';
import type { QueriesHandlerService } from '$lib/shared/handlers/query/queries-handler.service';
import type { IQueryResult } from '$lib/shared/handlers/query/query-result.interface';
import type { IQuery } from '$lib/shared/handlers/query/query.interface';
import type { AxiosResponse } from 'axios';
import { Observable, from, map } from 'rxjs';
import type { IDefaultersResponse } from './defaulters.interface';

export class DefaultersQuery implements IQuery<IDefaultersResponse> {
	constructor() {}

	execute(
		queriesHandler: QueriesHandlerService
	): Observable<IQueryResult<IDefaultersResponse>> {
		const url = `${PUBLIC_API_URL}/queries/admin/reports/defaulters`;
		return from(queriesHandler.axios.get<IQueryResult<IDefaultersResponse>>(url)).pipe(
			map((result: AxiosResponse<IQueryResult<IDefaultersResponse>>) => {
				if (!result || !result.data || !result.data.resultData)
					return {
						resultData: [],
						errorCode: EErrorCode.none
					} as unknown as IQueryResult<IDefaultersResponse>;

				return result.data;
			})
		);
	}
}
