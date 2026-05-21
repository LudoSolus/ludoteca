export function formatLongDate(date: Date, month: 'long' | 'short'): string {
	const formattedDate = new Intl.DateTimeFormat('pt-BR', {
		day: 'numeric',
		month: month
	}).format(date);

	return formattedDate;
}