import type { EErrorCode } from "$lib/shared/enums/error-code.enum";

export interface IQueryResult<T> {
  resultData: T[] | T;
  errorCode?: EErrorCode;
  errorName?: string;
  errorMessage?: string;
  page?: number;
  totalPages?: number;
}
