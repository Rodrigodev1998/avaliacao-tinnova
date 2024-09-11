import TableComponent from './components/Table'
import './App.css'
import { MantineProvider } from '@mantine/core'
import Formulario from './components/Form'
import { useState } from 'react';

function App() {
  const [refreshKey, setRefreshKey] = useState(0);

    const handleVeiculoCreated = () => {
        // Incrementa a chave de atualização para forçar o recarregamento da lista
        setRefreshKey((prevKey) => prevKey + 1);
    };
  return (
    <MantineProvider>
      <Formulario onVeiculoCreated={handleVeiculoCreated} />
      <TableComponent refreshVeiculos={() => setRefreshKey((prevKey) => prevKey + 1)} key={refreshKey} />
    </MantineProvider>
  )
}

export default App
