# NLM API Integration

Sponsored by: In-Game Event, A Red Flag Syndicate LLC.

# Important Note

> **⚠️ This project is NOT production-ready!**  
> Please use it for development and testing purposes only. 
> Ensure thorough testing and review before deploying to a live environment.

>
> **CURRENT WORK**
>
> - **Linux:**
>   - Docker:
>     - Redis working (In memory Cache.)
>     - HAProxy working (Load Balancing.)
>     - Prometheus working (For using observability into components.)
>     - redis_exporter working (For using observability into components.)
>     - Grafana working (For using observability into components.)
>     - Kafka running but not yet used (Events)
>
> - **Code:**
>   - ICD10 - functioning
>   - RXNorm - in progress

## Overview

The NLM API Integration project provides a seamless interface for interacting with the National Library of Medicine (
NLM) API. This library simplifies the complexities of making API calls, handling responses, and processing data for
applications in healthcare, research, and analytics.

Kafka will allow us to integrate any system in the future for plug and play.

## Architecture

This project utilizes a plain old Java Archive (JAR) to call the NLM REST API and return Java objects. Key features
include:

- **Caching**: A caching layer and Cassandra database to handle NLM API throttling.
- **Request Flow**:
    1. Facade class call
    2. Service Layer
    3. Cache check
    4. If not found, call NLM REST service
- **Messaging**: Success or failure notifications sent to Kafka, along with non-identifiable data (e.g., zip code,
  facility name, age ranges) for research purposes.

### Future Directions

Data collected may be used to create global maps, with an emphasis on centralized data gathering and failure reporting.

## Project Plans/Phases

1. Establish basic lookups with REST calls to the NLM API and develop test cases. Detect issues and notify user there
   was a problem with the ICD10 or RXNorm code.
2. Add in Database for saving the lookups for history and analytics.
3. Setup EMAIL Notification
3. Setup monitoring of system
4. Implement file reading from facilities to extract ICD-10/rxnorm/ndc codes and report invalid codes.
5. Crosswalk of ndc codes? 
6. Analytics on failed data 
7. Expand the dataset to include more patient information without identification. Example, passing in a guid to represent the
   person so we don't know anything about them, then we can build a more full validation system. 
8. Live Notification detection system. Example, a certain zip code suddenly gets high volume data for say flu. Map it out and
   lets people know to avoid that area or get them assistance. 
9. Maybe: Integrate AI for document interpretation, enabling automated lookups of codes. 
10. Yes I will take over the whole world muhahahahahahahahah,... Says the Brain!
11. Build out a full free Health Information Data Portfolio Exchange for a person that encompasses everything. People really need to see the bigger picture on their health.

## Components

- **ICD10ApiFacade**: Main interface for interacting with the ICD-10 API, abstracting complexities and allowing
  configuration of search parameters, caching options, and error handling.
- **ICD10ReturnedJson**: Represents the structure of the JSON response from the API, mapping fields to Java objects.
- **ICD10SearchResponse**: A wrapper for processing search results from the API, providing utility methods for accessing
  code descriptions and additional data fields.
- **RxNormFacade**: In progress, RXNorm lookup utilizing the REST API with caching support.
- **RxNormService**: Service layer for managing RXNorm-related functionality.

## Caching with Redis or Memcached

To improve performance and reduce API load, the application can use **Redis** or **Memcached** for caching responses:

- **Strategy**: Cache responses based on search parameters.
- **Benefits**: Reduces unnecessary API calls and speeds up response times for frequently searched terms.
- **Configuration**: Caching can be enabled or disabled through the `ICD10ApiFacade` interface.

## Future Enhancements with Kafka

The project plans to integrate with **Apache Kafka** for analytics and event-driven architecture:

- **Publish/Subscribe Model**: Publish search events and analytics data.
- **Real-time Analytics**: Analyze user search patterns and API usage metrics.
- **Scalability**: Kafka will enhance scalability for high-volume analytics without impacting performance.
- **Error Discovery**: Capture bad lookups for analysis.

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven
- Redis (Redis currently used.)
- Kafka

### Installation

> **⚠️ Currently only linux.docker-compose.yml works fully. Still need to fix windows.docker-compose

1) Run the docker for your system
2) loginto Grafana at http://localhost:3000
   3) user: admin
   4) password: admin
   5) Add promethus as a data source
   6) load the 763_rev6.json in the dashboard import
   7) load the haproy-2-full.json in the dashboard import
8) Have FUN!


Thanks go out to:

https://levelup.gitconnected.com/kraft-kafka-cluster-with-docker-e79a97d19f2c

#Future projects

1. Full HL7 validation, where we can with analytics and observability