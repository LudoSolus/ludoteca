import { PUBLIC_API_URL } from '$env/static/public';
import { EErrorCode } from '$lib/shared/enums/error-code.enum';
import type { QueriesHandlerService } from '$lib/shared/handlers/query/queries-handler.service';
import type { IQueryResult } from '$lib/shared/handlers/query/query-result.interface';
import type { IQuery } from '$lib/shared/handlers/query/query.interface';
import type { AxiosResponse } from 'axios';
import { Observable, from, map } from 'rxjs';
import type { IParticipantsByEventResponse } from './participants-by-event.interface';

export class ParticipantsByEventQuery implements IQuery<IParticipantsByEventResponse> {
	constructor() {}

	execute(queriesHandler: QueriesHandlerService): Observable<IQueryResult<IParticipantsByEventResponse>> {
		const url = `${PUBLIC_API_URL}/queries/admin/dashboard/participants-by-event`;
		return from(queriesHandler.axios.get<IQueryResult<IParticipantsByEventResponse>>(url)).pipe(
			map((result: AxiosResponse<IQueryResult<IParticipantsByEventResponse>>) => {
				if (!result || !result.data || !result.data.resultData)
					return { resultData: { data: [] }, errorCode: EErrorCode.none } as unknown as IQueryResult<IParticipantsByEventResponse>;

				return result.data;
			})
		);
	}
}
