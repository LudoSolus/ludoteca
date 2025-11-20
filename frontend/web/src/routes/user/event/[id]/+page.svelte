<script lang="ts">
	import type {
		GameDetailsForEvent,
		GetEventDetailsResponse
	} from '$lib/api/queries/get-event-details/get-event-details.interface';
	import EventDetails from '$lib/components/templates/EventDetails.svelte';
	import { ECategory } from '$lib/shared/enums/category.enum';

	const mockGames: GameDetailsForEvent[] = [
		{
			nameGame: 'Katan',
			isAvailable: false,
			category: ECategory.AventuraTematicos,
			minPlayers: 2,
			maxPlayers: 4
		},
		{
			nameGame: 'War',
			isAvailable: true,
			category: ECategory.Destreza,
			minPlayers: 3,
			maxPlayers: 6
		},
		{
			nameGame: 'Banco imobiliário',
			isAvailable: false,
			category: ECategory.Deducao,
			minPlayers: 3,
			maxPlayers: 6
		},
		{
			nameGame: 'Banco imobiliário',
			isAvailable: false,
			category: ECategory.Deducao,
			minPlayers: 3,
			maxPlayers: 6
		},
		{
			nameGame: 'Banco imobiliário',
			isAvailable: false,
			category: ECategory.Deducao,
			minPlayers: 3,
			maxPlayers: 6
		},
		{
			nameGame: 'Banco imobiliário',
			isAvailable: false,
			category: ECategory.Deducao,
			minPlayers: 3,
			maxPlayers: 6
		},
		{
			nameGame: 'Banco imobiliário',
			isAvailable: false,
			category: ECategory.Deducao,
			minPlayers: 3,
			maxPlayers: 6
		},
		{
			nameGame: 'Banco imobiliário',
			isAvailable: false,
			category: ECategory.Deducao,
			minPlayers: 3,
			maxPlayers: 6
		},
		{
			nameGame: 'Banco imobiliário',
			isAvailable: false,
			category: ECategory.Deducao,
			minPlayers: 3,
			maxPlayers: 6
		}
	];

	const mockEvent: GetEventDetailsResponse = {
		name: 'Evento de Haloween',
		startDate: new Date(),
		finalDate: new Date(),
		street: 'Ivo Afonso Zanini',
		number: 67,
		supplement: 'casa dos fundos',
		neighborhood: 'Centro',
		city: 'Cornélio Procópio',
		state: 'Paraná',
		zipCode: '86300070',
		listGames: mockGames
	};
</script>

<EventDetails eventData={mockEvent} />
