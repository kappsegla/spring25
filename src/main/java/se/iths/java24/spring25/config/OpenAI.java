package se.iths.java24.spring25.config;

import org.springframework.ai.model.NoopApiKey;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.net.http.HttpClient;
import java.time.Duration;

//@Configuration
//public class OpenAI {
//
//    @Bean
//    OpenAiApi openAiApi(){
//        return OpenAiApi.builder()
//                .baseUrl("http://127.0.0.1:1219")
//                .apiKey(new NoopApiKey())
//                .restClientBuilder(RestClient.builder()
//                        // Force HTTP/1.1 for non-streaming
//                        .requestFactory(new JdkClientHttpRequestFactory(HttpClient.newBuilder()
//                                .version(HttpClient.Version.HTTP_1_1)
//                                .connectTimeout(Duration.ofSeconds(30))
//                                .build())))
//                .build();
//    }
//
//    @Bean
//    OpenAiChatOptions openAiChatOptions(){
//        return OpenAiChatOptions.builder()
//                .temperature(0.4)
//                .build();
//    }
//
//    @Bean
//    OpenAiChatModel openAiChatModel(OpenAiApi openAiApi,OpenAiChatOptions options){
//        return OpenAiChatModel.builder().openAiApi(openAiApi).defaultOptions(options).build();
//    }
//}
