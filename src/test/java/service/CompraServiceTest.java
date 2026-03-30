package service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompraServiceTest {
    @Mock
    ProveedorService proveedorService;

    @Mock
    CatalogoService catalogoService;

    @Mock
    AprobacionService aprobacionService;

    @InjectMocks
    CompraService compraService;

    @Test
    void deberiaFallarSiProveedorInactivo() {

        when(proveedorService.estaActivo("P1")).thenReturn(false);

        String resultado = compraService.registrarCompra(
                "P1",
                Arrays.asList(100.0, 200.0),
                300,
                LocalDate.now()
        );

        assertEquals("Proveedor inactivo", resultado);
    }

    @Test
    void deberiaRequerirAprobacionSiSuperaMonto() {

        when(proveedorService.estaActivo("P1")).thenReturn(true);
        when(catalogoService.precioValido(anyDouble())).thenReturn(true);
        when(aprobacionService.tieneAprobacion()).thenReturn(false);

        String resultado = compraService.registrarCompra(
                "P1",
                Arrays.asList(500.0, 600.0),
                1100,
                LocalDate.now()
        );

        assertEquals("Compra requiere aprobación", resultado);
    }
}