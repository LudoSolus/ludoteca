<script lang="ts">
	import { goto } from '$app/navigation';
	import { CreateBoardGameCommand } from '$lib/api/commands/board-games/create-board-game/create-board-game.command';
	import type { ICreateBoardGameRequest } from '$lib/api/commands/board-games/create-board-game/create-board-game.interface';
	import BoardGamesRegister from '$lib/components/templates/admin/BoardGamesRegister.svelte';
	import { CommandsHandlerService } from '$lib/shared/handlers/command/commands-handler.service';
	import axios from 'axios';
	import { toast } from 'svoast';

	const commandsHandler = new CommandsHandlerService(axios);
	let isLoading: boolean = false;

	function createBoardGame(data: ICreateBoardGameRequest) {
		isLoading = true;
		const command = new CreateBoardGameCommand(data);
		commandsHandler.handle(command).subscribe({
			next: (data) => {
				goto('/admin/board-games');
				isLoading = false;
			},
			error: (err) => {
				isLoading = false;
			}
		});
	}
</script>

<BoardGamesRegister {isLoading} onCreateBoardGame={createBoardGame} />
