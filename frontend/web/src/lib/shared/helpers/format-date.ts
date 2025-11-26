export function formatDate(date: Date, withYear: boolean = false): string {
	return date.toLocaleDateString('pt-BR', {
		day: '2-digit',
		month: '2-digit',
		year: withYear ? 'numeric' : undefined
	});
}
