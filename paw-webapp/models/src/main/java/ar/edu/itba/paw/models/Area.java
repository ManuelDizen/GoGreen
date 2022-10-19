package ar.edu.itba.paw.models;

import java.util.Objects;

public enum Area {

    AGRONOMIA(0, "Agronomía"),
    ALMAGRO(1, "Almagro"),
    ALMIRANTE_BROWN(2, "Almirante Brown"),
    AVELLANEDA(3, "Avellaneda"),
    BALVANERA(4, "Balvanera"),
    BELGRANO(5, "Belgrano"),
    BERAZATEGUI(6, "Berazategui"),
    BOEDO(7, "Boedo"),
    CABALLITO(8, "Caballito"),
    CHACARITA(9, "Chacarita"),
    COGHLAN(10, "Coghlan"),
    COLEGIALES(11, "Colegiales"),
    CONSITUTICION(12, "Constitución"),
    ECHEVERRIA(13, "Esteban Echeverría"),
    EZEIZA(14, "Ezeiza"),
    FLORENCIO_VARELA(15,"Florencio Varela"),
    FLORES(16, "Flores"),
    FLORESTA(17, "Floresta"),
    HURLINGHAM(18, "Hurlingham"),
    ITUZAINGO(19, "Ituzaingó"),
    JOSE_C_PAZ(20, "José C. Paz"),
    LA_BOCA(21, "La Boca"),
    LANUS(22, "Lanús"),
    PATERNAL(23, "La Paternal"),
    LINIERS(24, "Liniers"),
    LOMAS(25, "Lomas de Zamora"),
    LA_MATANZA(26, "La Matanza"),
    MALVINAS(27, "Malvinas Argentinas"),
    MATADEROS(28, "Mataderos"),
    MERLO(29, "Merlo"),
    MONTE_CASTRO(30, "Monte Castro"),
    MONTSERRAT(31, "Montserrat"),
    MORENO(32, "Moreno"),
    MORON(33, "Morón"),
    POMPEYA(34, "Nueva Pompeya"),
    NUÑEZ(35, "Núñez"),
    PALERMO(36, "Palermo"),
    PARQUE_AVELLANEDA(37, "Parque Avellaneda"),
    PARQUE_CHACABUCO(38, "Parque Chacabuco"),
    PARQUE_CHAS(39, "Parque Chás"),
    PARQUE_PATRICIOS(40, "Parque Patricios"),
    PUERTO_MADERO(41, "Puerto Madero"),
    RECOLETA(42, "Recoleta"),
    RETIRO(43, "Retiro"),
    SAAVEDRA(44, "Saavedra"),
    SAN_CRISTOBAL(45, "San Cristóbal"),
    SAN_FERNANDO(46, "San Fernando"),
    SAN_ISIDRO(47, "San Isidro"),
    SAN_MARTIN(48, "San Martín"),
    SAN_MIGUEL(49, "San Miguel"),
    SAN_NICOLAS(50, "San Nicolás"),
    SAN_TELMO(51, "San Telmo"),
    TIGRE(52, "Tigre"),
    TRES_DE_FEBRERO(53, "Tres de Febrero"),
    VERSALLES(54, "Versalles"),
    VICENTE_LOPEZ(55, "Vicente López"),
    VILLA_CRESPO(56, "Villa Crespo"),
    DEVOTO(57, "Villa Devoto"),
    VILLA_GENERAL_MITRE(58, "Villa General Mitre"),
    LUGANO(59, "Villa Lugano"),
    VILLA_LURO(60, "Villa Luro"),
    VILLA_ORTUZAR(61, "Villa Ortúzar"),
    VILLA_PUEYRREDON(62, "Villa Pueyrredón"),
    VILLA_REAL(63, "Villa Real"),
    VILLA_RIACHUELO(64, "Villa Riachuelo"),
    VILLA_SANTA_RITA(65, "Villa Santa Rita"),
    SOLDATI(66, "Villa Soldati"),
    VILLA_URQUIZA(67, "Villa Urquiza"),
    VILLA_DEL_PARQUE(68, "Villa del Parque"),
    VELEZ_SARSFIELD(69, "Vélez Sársfield");

    private final Long id;

    private final String name;

    Area(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Area getById(Long id) {
        for(Area area : Area.values()) {
            if(Objects.equals(area.getId(), id))
                return area;
        }
        return null;
    }
}
