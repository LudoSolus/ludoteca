<script lang="ts">
	import { faUsers } from '@fortawesome/free-solid-svg-icons';
	import Fa from 'svelte-fa';

	export let totalUsers: number = 0;
</script>

<div
	class="flex h-full mx-auto w-full max-w-70 flex-col items-center justify-center rounded-lg border border-gray-200 p-6"
>
	<div class="flex h-16 w-16 items-center justify-center mb-4">
		<Fa icon={faUsers} class="text-3xl text-purple-600" />
	</div>
	<h4 class="mb-2 text-center text-xl">Total de Usuários Cadastrados</h4>
	<p class="text-4xl font-bold">{totalUsers}</p>
</div>
