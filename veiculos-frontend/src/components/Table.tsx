import { Button, Modal, Radio, RadioGroup, Table, TextInput } from "@mantine/core";
import { useEffect, useState } from "react";
import { Veiculo } from "../types/Veiculo";
import { FiEdit2, FiTrash } from "react-icons/fi";
import './Table.css'

interface TableComponentProps {
  refreshVeiculos: () => void;
}
export default function TableComponent({ refreshVeiculos }: TableComponentProps) {
    const [veiculos, setVeiculos] = useState<Veiculo[]>([]);
    const [error, setError] = useState<string | null>(null);
    const apiUrl = import.meta.env.VITE_API_URL;
    const [filters, setFilters] = useState({ ano: '', cor: '', marca: '' });
    const [editingVeiculo, setEditingVeiculo] = useState<Veiculo | null>(null);
    const [formValues, setFormValues] = useState<Partial<Veiculo>>({});
    const [modalOpen, setModalOpen] = useState(false);
    const [isFiltering, setIsFiltering] = useState(true);

  const fetchVeiculos = async () => {
    const query = isFiltering ? new URLSearchParams(filters).toString() : '';
    const url = `${apiUrl}/veiculos${query ? `/filter?${query}` : ''}`;

    try {
      const response = await fetch(url);
      if (!response.ok) {
        throw new Error('Erro na requisição');
      }
      const data = await response.json();
      setVeiculos(data);
    } catch (error) {
      console.error('Erro na requisição:', error);
      setError('Erro ao buscar veículos');
    }
  };

  useEffect(() => {
    fetchVeiculos();
  }, [apiUrl, filters, refreshVeiculos, isFiltering]);

    useEffect(() => {
      const fetchVeiculos = async () => {
          try {
              const response = await fetch(`${apiUrl}/veiculos`);
              if (!response.ok) {
                  throw new Error('Erro na requisição');
              }
              const data = await response.json();
              setVeiculos(data);
          } catch (error) {
              console.error('Erro na requisição:', error);
              setError('Erro ao buscar veículos');
          }
      };

      fetchVeiculos();
  }, [apiUrl]);

  const handleDelete = async (id: string) => {
    try {
        const response = await fetch(`${apiUrl}/veiculos/${id}`, {
            method: 'DELETE',
        });
        if (!response.ok) {
            throw new Error('Erro na exclusão');
        }
        setVeiculos((prevVeiculos) => prevVeiculos.filter((veiculo) => veiculo.id !== id));
    } catch (error) {
        console.error('Erro na exclusão:', error);
        setError('Erro ao excluir veículo');
    }
};

const handleEdit = (veiculo: Veiculo) => {
  setEditingVeiculo(veiculo);
  setFormValues(veiculo);
  setModalOpen(true);
};

const handleFormChange = (e: React.ChangeEvent<HTMLInputElement>) => {
  const { name, value } = e.target;
  setFormValues((prevValues) => ({ ...prevValues, [name]: value }));
};

const handleRadioChange = (value: string) => {
  setFormValues((prevValues) => ({ ...prevValues, vendido: value === 'true' }));
};

const handleFormSubmit = async () => {
  if (!editingVeiculo) return;

  try {
      const response = await fetch(`${apiUrl}/veiculos/${editingVeiculo.id}`, {
          method: 'PUT',
          headers: {
              'Content-Type': 'application/json',
          },
          body: JSON.stringify(formValues),
      });
      if (!response.ok) {
          throw new Error('Erro na atualização');
      }
      setVeiculos((prevVeiculos) =>
          prevVeiculos.map((veiculo) =>
              veiculo.id === editingVeiculo.id ? { ...veiculo, ...formValues } : veiculo
          )
      );
      setModalOpen(false);
  } catch (error) {
      console.error('Erro na atualização:', error);
      setError('Erro ao atualizar veículo');
  }
};

const handleFilterChange = (e: React.ChangeEvent<HTMLInputElement>) => {
  const { name, value } = e.target;
  setFilters((prevFilters) => ({ ...prevFilters, [name]: value }));
};

const handleClearFilters = () => {
  setFilters({ ano: '', cor: '', marca: '' });
  setIsFiltering(false);
};

const handleApplyFilters = () => {
  setIsFiltering(true)
};

 return (
  <>
    <h2>Lista de Veiculos</h2>
    <div className="area-filter" style={{ marginBottom: '20px' }}>
        <TextInput
            placeholder="Ano"
            name="ano"
            value={filters.ano}
            onChange={handleFilterChange}
            style={{ marginRight: '10px' }}
        />
        <TextInput
            placeholder="Cor"
            name="cor"
            value={filters.cor}
            onChange={handleFilterChange}
            style={{ marginRight: '10px' }}
        />
        <TextInput
            placeholder="Marca"
            name="marca"
            value={filters.marca}
            onChange={handleFilterChange}
            style={{ marginRight: '10px' }}
        />
      <div className="area-filter-bnt">
          <Button onClick={handleApplyFilters}>Aplicar Filtros</Button>
          <Button onClick={handleClearFilters}>Limpar Filtros</Button>
      </div>
    </div>
    <Table>
    <Table.Thead>
      <Table.Tr>
        <Table.Th>Veiculo</Table.Th>
        <Table.Th>Descrição</Table.Th>
        <Table.Th>Marca</Table.Th>
        <Table.Th>Cor</Table.Th>
        <Table.Th>Ano</Table.Th>
        <Table.Th>Status de venda</Table.Th>
        <Table.Th>Ações</Table.Th>
      </Table.Tr>
    </Table.Thead>
    <Table.Tbody>
    {veiculos.length > 0 ? (
        veiculos.map((veiculo) => (
            <Table.Tr key={veiculo.id}>
                <Table.Td>{veiculo.veiculo}</Table.Td>
                <Table.Td>{veiculo.descricao}</Table.Td>
                <Table.Td>{veiculo.marca}</Table.Td>
                <Table.Td>{veiculo.cor}</Table.Td>
                <Table.Td>{veiculo.ano}</Table.Td>
                <Table.Td>{veiculo.vendido ? 'Vendido' : 'Não vendido'}</Table.Td>
                <Table.Td>
                    <button 
                    className="btn-edit" 
                    onClick={() => handleEdit(veiculo)}>
                      <FiEdit2 size={14} color="white"/>
                    </button>
                    <button 
                    className="btn-trash" 
                    onClick={() => handleDelete(veiculo.id)}>
                      <FiTrash size={14} color="white" />
                    </button>
                </Table.Td>
            </Table.Tr>
        ))
    ) : (
        <Table.Tr>
            <Table.Td colSpan={7} style={{ textAlign: 'center' }}>
                <h3>Nenhum veículo encontrado.</h3>
            </Table.Td>
        </Table.Tr>
    )}
    </Table.Tbody>
  </Table>
  <Modal opened={modalOpen} onClose={() => setModalOpen(false)} title="Editar Veículo">
    {editingVeiculo && (
      <>
          <TextInput
              label="Veículo"
              name="veiculo"
              value={formValues.veiculo || ''}
              onChange={handleFormChange}
          />
          <TextInput
              label="Descrição"
              name="descricao"
              value={formValues.descricao || ''}
              onChange={handleFormChange}
          />
          <TextInput
              label="Marca"
              name="marca"
              value={formValues.marca || ''}
              onChange={handleFormChange}
          />
          <TextInput
              label="Cor"
              name="cor"
              value={formValues.cor || ''}
              onChange={handleFormChange}
          />
          <TextInput
              label="Ano"
              name="ano"
              value={formValues.ano || ''}
              onChange={handleFormChange}
          />
          <div className="edit-radio">
            <label>Status de venda</label>
            <RadioGroup
                value={formValues.vendido ? 'true' : 'false'}
                onChange={handleRadioChange}
            >
                <Radio value="true" label="Vendido" className="btn-vendido" />
                <Radio value="false" label="Não vendido"/>
            </RadioGroup>
        </div>
          <br />
          <Button onClick={handleFormSubmit}>Salvar</Button>
      </>
    )}
  </Modal>
  </>
 );
}