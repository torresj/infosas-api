package com.torresj.infosas.dtos;

import java.util.Map;

import static java.util.Map.entry;

public class ExclusionReasons {
    private static Map<String,String> EXCLUSION_REASONS = Map.ofEntries(
            entry("A01", "LA PERSONA SUPERA PROCESO ACCESO EMPLEO FIJO SIN TOMA DE POSESIÓN: La Comisión no ha valorado su expediente porque ha superado y/o se ha propuesto su nombramiento como personal fijo en la misma categoría y especialidad en el Servicio Andaluz de Salud."),
            entry("E01", "EDAD MÍNIMA: No tener cumplida la edad mínima legalmente establecida."),
            entry("E02", "SOLICITUD CENTRO: No haber solicitado ningún centro para la categoría y/o especialidad a la que se opta."),
            entry("E03", "DESISTIMIENTO: Desistimiento."),
            entry("E04", "INCAPACIDAD PERMANENTE: Tiene reconocida una invalidez total para el ejercicio de las funciones propias de esta categoría o tiene reconocida una invalidez absoluta."),
            entry("E05", "DISCAPACIDAD: No acreditar discapacidad (sólo para los que alegaron y registraron porcentaje igual o superior al 33%), se incorpora al turno libre."),
            entry("E06", "JUBILACIÓN: Superar la edad de jubilación."),
            entry("E07", "IDENTIFICADAS MANIFIESTAS INEXACTITUDES: Identificación de manifiestas inexactitudes, falsedad u omisión, de carácter esencial, de datos o informaciones incorporadas en su solicitud bajo declaración responsable."),
            entry("E08", "PERMISO DE CONDUCIR: No acredita estar en posesión del permiso de conducir clase exigido en las bases de la convocatoria."),
            entry("E09", "LICENCIA DE OPERADOR/A DE INSTALACIONES RADIACTIVAS: No acredita estar en posesión de la licencia de operador/a de instalaciones radiactivas expedida por el Consejo de Seguridad Nuclear, campo de aplicación medicina nuclear, en vigor."),
            entry("E10", "ACREDITA TITULACIÓN PERO NO LA EXPERIENCIA PROFESIONAL: No acredita la experiencia profesional o el desempeño de funciones exigido en la convocatoria como requisito previsto para el acceso."),
            entry("E11", "NO ACREDITA ESTAR EN POSESIÓN DE LA TITULACIÓN REQUERIDA: No acredita estar en posesión de la titulación requerida para el acceso. La titulación presentada no es equivalente a la titulación exigida en las bases de la convocatoria."),
            entry("E12", "NACIONALIDAD: No acredita poseer la nacionalidad española, ni en su defecto encontrarse en alguno de los supuestos establecidos en las bases de la convocatoria."),
            entry("E13", "POSEER LA CONDICIÓN DE PERSONAL FIJO EN LA CATEGORÍA O LA CONDICIÓN DE FUNCIONARIO DE CARRERA EN LA ESPECIALIDAD A LA QUE OPTA: Poseer la condición de personal fijo en la categoría y régimen jurídico."),
            entry("E14", "FALLECIMIENTO: Fallecimiento."),
            entry("E15", "EXPERIENCIA PROFESIONAL FORMACIÓN TEÓRICO-PRÁCTICA ÁREA: No acreditar experiencia profesional en área específica solicitada ni formación teórico-práctica en la misma."),
            entry("E16", "CERTIFICACIÓN HABILITACIÓN: No acreditar certificado de habilitación de trabajadoras y trabajadores experimentados para la conducción de ambulancias asistenciales (B y C)."),
            entry("E17", "CUALIFICACIÓN INSTALADOR/A: No acreditar cualificación como instalador/a electricista de alta y baja tensión, según normativa vigente."),
            entry("E18", "CUALIFICACIÓN CALDERA: No acreditar cualificación como Operador industrial de calderas, según la normativa vigente y poseer el curso autorizado por la Consejería competente en materia de salud para el mantenimiento higiénico sanitario de instalaciones de riesgo frente a la legionella."),
            entry("E19", "CERTIFICADO DE PROFESIONALIDAD: No acreditar Certificado de Profesionalidad nivel 3 en electromedicina. Gestión y supervisión de la instalación y mantenimiento de sistemas de electromedicina."),
            entry("E20", "PARTICIPA EN LIBRE Y PI: Las personas aspirantes habrán de señalar en su solicitud el sistema de acceso por el que concurren: Turno libre o promoción interna temporal, no pudiendo concurrir por ambos. Se ha registrado una solicitud al turno de promoción interna: No se acredita los requisitos para acceder por el turno de promoción interna en la categoría o se ha solicitado el acceso en ambos sistemas de acceso, resultando excluido en uno de ellos."),
            entry("E21", "AUTOBAREMO: No realiza en el plazo habilitado para ello autobaremo de sus méritos de acuerdo con lo establecido en la convocatoria."),
            entry("S21", "NO REALIZA AUTOBAREMO EN PLAZO Y SE LE REQUIERE PARA SUBSANAR SUS MÉRITOS DE OFICIO AL ESTAR INSCRITO EN BOLSA ÚNICA HASTA CORTE 2021: No realiza en el plazo habilitado para ello autobaremo de sus méritos de acuerdo con lo establecido en la convocatoria y se le requiere para que subsane la presentación del autobaremo con sus méritos de oficio."),
            entry("E22", "PROMOCION INTERNA DESDE UNA CATEGORIA CON SUPERIOR NIVEL ACADÉMICO EXIGIDO: El titulo exigido para el ingreso en la categoría de procedencia debe ser de igual o inferior nivel académico al requerido en la categoría convocada, y sin perjuicio del número de niveles existentes entre ambos títulos.")
    );

    public static ExclusionReasonDto getExclusionReason(String code) {
        return code == null ? null : new ExclusionReasonDto(code,EXCLUSION_REASONS.get(code));
    }
}
