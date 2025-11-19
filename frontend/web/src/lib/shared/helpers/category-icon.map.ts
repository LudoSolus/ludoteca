import type { SvelteComponent } from 'svelte';
import type { IconDefinition } from '@fortawesome/free-solid-svg-icons';
import { 
  faChess,
  faHandshake,
  faMasksTheater,
  faPuzzlePiece,
  faMagnifyingGlass,
  faBolt,
  faGamepad,

} from '@fortawesome/free-solid-svg-icons';

import { ECategory } from '$lib/shared/enums/category.enum';

import PersonDiceIcon from '$lib/components/atoms/icons/PersonDiceIcon.svelte';
import AbstractCubeIcon from '$lib/components/atoms/icons/AbstractCubeIcon.svelte';
import MapAdventureIcon from '$lib/components/atoms/icons/MapAdventureIcon.svelte';
import ComercialIcon from '$lib/components/atoms/icons/ComercialIcon.svelte';

export const categoryIconMap: Record<ECategory, IconDefinition | typeof SvelteComponent | string> = {
  [ECategory.Estrategia]: faChess,
  [ECategory.Cooperativos]: faHandshake,
  [ECategory.Deducao]: faMagnifyingGlass,
  [ECategory.PartyGame]: PersonDiceIcon as typeof SvelteComponent,
  [ECategory.BlefeEnganacao]: faMasksTheater,
  [ECategory.Destreza]: faBolt,
  [ECategory.JogosAbstratos]: AbstractCubeIcon as typeof SvelteComponent,
  [ECategory.LogicaQuebraCabeca]: faPuzzlePiece,
  [ECategory.AventuraTematicos]: MapAdventureIcon as typeof SvelteComponent,
  [ECategory.Negociacao]: ComercialIcon as typeof SvelteComponent,
  [ECategory.Outros]: faGamepad
}