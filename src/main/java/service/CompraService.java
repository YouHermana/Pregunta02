package service;
import java.time.LocalDate;
import java.util.List;


public class CompraService{
private ProveedorService proveedorService;
private CatalogoService catalogoService;
private AprobacionService aprobacionService;

public CompraService(ProveedorService proveedorService,
                     CatalogoService catalogoService,
                     AprobacionService aprobacionService) {
    this.proveedorService = proveedorService;
    this.catalogoService = catalogoService;
    this.aprobacionService = aprobacionService;
}

public String registrarCompra(String proveedorId,
                              List<Double> precios,
                              double total,
                              LocalDate fechaEntrega) {

    if (total > 1000 && !aprobacionService.tieneAprobacion()) {
        return "Compra requiere aprobación";
    }

    if (fechaEntrega == null) {
        return "Fecha de entrega obligatoria";
    }

    double impuesto = total * 0.18;

    return "Compra registrada con impuesto: " + impuesto;
}
}
