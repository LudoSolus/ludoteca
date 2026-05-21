export function formatLongDate(date: Date): string {
	const formattedDate = new Intl.DateTimeFormat('pt-BR', {
		day: 'numeric',
		month: 'long'
	}).format(date);

	return formattedDate;
}