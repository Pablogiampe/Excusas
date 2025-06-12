# Parcial Giampetruzzi - Sistema de Gestión de Excusas

## Descripción
Sistema para la empresa ficticia "Excusas S.A." que gestiona las excusas de empleados que llegan tarde mediante una cadena de encargados especializados. El proyecto implementa varios patrones de diseño para asegurar un código robusto, flexible y mantenible.

## Patrones de Diseño Implementados

### 1. Chain of Responsibility (Cadena de Responsabilidad)
- **Propósito**: Permite que múltiples objetos tengan la oportunidad de manejar una solicitud sin acoplar el emisor con los receptores.
- **Implementación**:
  - `ManejadorExcusas` como la interfaz del handler.
  - `EncargadoBase` como la clase base abstracta para los eslabones de la cadena.
  - `Recepcionista`, `SupervisorArea`, `GerenteRRHH`, y `CEO` como handlers concretos.
  - `Rechazador` como el handler final por defecto.
- **Justificación**: Ideal para este problema, ya que cada tipo de excusa (`TRIVIAL`, `MODERADA`, etc.) es una solicitud que debe ser evaluada por una línea de encargados hasta que uno de ellos la procesa.

### 2. Template Method (Método Plantilla)
- **Propósito**: Define el esqueleto de un algoritmo en una operación, delegando la definición de algunos pasos a las subclases.
- **Implementación**:
  - `EncargadoBase` define el método `final manejarExcusa(Excusa excusa)`, que actúa como la plantilla.
  - Este método llama a una secuencia de pasos fijos, incluyendo los métodos abstractos (`hooks`) `esResponsable(excusa)` y `procesarExcusa(excusa)`.
  - Cada subclase (`Recepcionista`, `SupervisorArea`, etc.) implementa estos `hooks` para proveer su lógica específica sin alterar la estructura general del algoritmo.
- **Justificación**: Asegura que todos los encargados sigan el mismo flujo de procesamiento (verificar responsabilidad, ejecutar acción adicional, procesar), pero permite que cada uno defina su comportamiento particular. Esto crea un sistema más robusto y predecible.

### 3. Observer (Observador)
- **Propósito**: Define una dependencia uno-a-muchos entre objetos para que cuando un objeto cambie de estado, todos sus dependientes sean notificados y se actualicen automáticamente.
- **Implementación**:
  - `Observable` es una clase abstracta que maneja la lógica de suscripción y notificación.
  - `Observer` es la interfaz para los objetos que deben ser notificados.
  - `AdministradorProntuarios` es el sujeto observable (hereda de `Observable`).
  - `CEO` es un observador (implementa `Observer`) que se actualiza cuando se guarda un nuevo prontuario.
- **Justificación**: Permite notificar a todas las partes interesadas (en este caso, todos los CEOs) sobre la creación de un nuevo prontuario sin acoplar fuertemente al `AdministradorProntuarios` con los `CEOs`.

### 4. Strategy (Estrategia)
- **Propósito**: Define una familia de algoritmos, los encapsula y los hace intercambiables.
- **Implementación**:
  - `ModoResolucion` es la interfaz de la estrategia.
  - `ModoNormal`, `ModoVago`, y `ModoProductivo` son las estrategias concretas que definen acciones adicionales durante el procesamiento de la excusa.
  - Cada `EncargadoBase` tiene una instancia de `ModoResolucion`, que puede ser cambiada en tiempo de ejecución.
- **Justificación**: Permite variar el comportamiento de los encargados (por ejemplo, hacer que uno notifique al CTO en "modo productivo") sin tener que modificar la clase del encargado, favoreciendo el principio de Abierto/Cerrado.

### 5. Singleton
- **Propósito**: Garantiza que una clase tenga una sola instancia y proporciona un punto de acceso global a ella.
- **Implementación**: `AdministradorProntuarios` se implementa como un Singleton para asegurar que solo exista un registro central de prontuarios en toda la aplicación.
- **Justificación**: Es esencial que haya una única fuente de verdad para los prontuarios de los empleados. El patrón Singleton previene la creación de múltiples instancias que podrían llevar a inconsistencias de datos.

## Casos de Uso

1.  **Procesar Excusa Trivial**: Un `Empleado` genera una excusa por "quedarse dormido". La `LineaEncargados` la pasa al `Recepcionista`, quien la procesa y notifica.
2.  **Procesar Excusa Moderada**: Se genera una excusa por "corte de luz". El `SupervisorArea` la maneja, realizando verificaciones adicionales.
3.  **Procesar Excusa Compleja**: El `GerenteRRHH` recibe y analiza excusas que requieren una evaluación más profunda.
4.  **Procesar Excusa Inverosímil**: El `CEO` maneja excusas inverosímiles, las aprueba por su creatividad y genera un prontuario.
5.  **Rechazar Excusa**: Si una excusa no corresponde a ningún tipo manejado, llega al `Rechazador` al final de la cadena.
6.  **Notificar Prontuario**: Cuando el `CEO` guarda un `Prontuario`, el AdministradorProntuarios notifica a todos los `Observer` suscritos (otros CEOs).

