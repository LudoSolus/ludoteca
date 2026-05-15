<script lang="ts">
	import { faUsers } from '@fortawesome/free-solid-svg-icons';
	import Fa from 'svelte-fa';

	export let totalUsers: number = 0;
</script>

<div
	class="flex h-full w-full flex-col items-center justify-center rounded-lg border border-gray-200 bg-transparent p-6"
>
	<div
		class="bg-primary-100 text-primary-600 mb-4 flex h-16 w-16 items-center justify-center rounded-full"
	>
		<Fa icon={faUsers} class="text-3xl text-purple-600" />
	</div>
	<h3 class="mb-2 text-lg font-semibold text-gray-600">Total de Usuários Cadastrados</h3>
	<p class="text-4xl font-bold text-gray-800">{totalUsers}</p>
</div>
