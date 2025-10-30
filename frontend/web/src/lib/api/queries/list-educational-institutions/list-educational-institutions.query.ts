import type { QueriesHandlerService } from '$lib/shared/handlers/query/queries-handler.service';
import type { IQuery } from '$lib/shared/handlers/query/query.interface';
import { from, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import type { IEducationalInstitution } from './list-educational-institutions.interface';
import type { AxiosResponse } from 'axios';
import { PUBLIC_API_URL } from '$env/static/public';
import type { IQueryResult } from '$lib/shared/handlers/query/query-result.interface';
import { EErrorCode } from '$lib/shared/enums/error-code.enum';


export class ListEducationalInstitutionsQuery implements IQuery<IEducationalInstitution[]> {
    constructor() { }

    execute(
        queriesHandler: QueriesHandlerService
    ): Observable<IQueryResult<IEducationalInstitution[]>> {
        const url = `${PUBLIC_API_URL}/queries/educational-institutions/list-all-educational-institutions`;
        return from(queriesHandler.axios.get<IQueryResult<IEducationalInstitution[]>>(url)).pipe(
            map((result: AxiosResponse<IQueryResult<IEducationalInstitution[]>>) => {
                if (!result || !result.data || !result.data.resultData)
                    return { resultData: [], errorCode: EErrorCode.none } as IQueryResult<IEducationalInstitution[]>;
                return result.data;
            })
        );
    }
}