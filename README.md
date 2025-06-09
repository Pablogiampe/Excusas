# Parcial Giampetruzzi - Sistema de Gestión de Excusas

## Descripción
Sistema para la empresa ficticia "Excusas S.A." que gestiona las excusas de empleados que llegan tarde mediante una cadena de encargados especializados.

## Patrones de Diseño Implementados

### 1. Chain of Responsibility (Cadena de Responsabilidad)
- **Propósito**: Permite que múltiples objetos tengan la oportunidad de manejar una solicitud sin acoplar el emisor con los receptores.
- **Implementación**: 
  - `EncargadoBase` como handler abstracto
  - `Recepcionista`, `SupervisorArea`, `GerenteRRHH`, `CEO` como handlers concretos
  - `Rechazador` como handler por defecto
- **Justificación**: Cada tipo de excusa debe ser evaluada por un encargado específico, y si no puede procesarla, la pasa al siguiente en la línea.

### 2. Observer (Observador)
- **Propósito**: Define una dependencia uno-a-muchos entre objetos para que cuando un objeto cambie de estado, todos sus dependientes sean notificados.
- **Implementación**:
  - `Observable` e `Observer` como interfaces
  - `AdministradorProntuarios` como sujeto observable
  - `CEO` como observador
- **Justificación**: Cuando se crea un nuevo prontuario, todos los CEOs deben ser notificados.

### 3. Strategy (Estrategia)
- **Propósito**: Define una familia de algoritmos, los encapsula y los hace intercambiables.
- **Implementación**:
  - `ModoResolucion` como interfaz de estrategia
  - `ModoNormal`, `ModoVago`, `ModoProductivo` como estrategias concretas
- **Justificación**: Cada encargado puede tener diferentes formas de resolver las excusas.

### 4. Singleton
- **Propósito**: Garantiza que una clase tenga una sola instancia y proporciona un punto de acceso global.
- **Implementación**: `AdministradorProntuarios`
- **Justificación**: Solo debe existir un administrador de prontuarios para toda la empresa.



## Casos de Uso

1. **Procesar Excusa Trivial**: El recepcionista maneja excusas como "me quedé dormido".
2. **Procesar Excusa Moderada**: El supervisor maneja excusas como "se cortó la luz".
3. **Procesar Excusa Compleja**: El gerente de RRHH maneja excusas complejas.
4. **Procesar Excusa Inverosímil**: El CEO maneja excusas extremadamente inverosímiles.
5. **Rechazar Excusa**: Si ningún encargado puede manejar la excusa, se rechaza.
6. **Notificar Prontuario**: Cuando se crea un prontuario, se notifica a todos los CEOs.

