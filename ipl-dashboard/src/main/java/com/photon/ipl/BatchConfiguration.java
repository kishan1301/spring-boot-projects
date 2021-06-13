package com.photon.ipl;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.photon.ipl.data.MatchInputData;
import com.photon.ipl.models.Match;
import com.photon.ipl.processor.MatchProcesssor;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	private final String[] FIELD_NAMES = { "id", "city", "date", "player_of_match", "venue", "neutral_venue", "team1",
			"team2", "toss_winner", "toss_decision", "winner", "result", "result_margin", "eliminator", "method",
			"umpire1", "umpire2" };

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Bean
	public FlatFileItemReader<MatchInputData> reader() {
		return new FlatFileItemReaderBuilder<MatchInputData>().name("matchItemReader")
				.resource(new ClassPathResource("matches-data.csv")).delimited().names(FIELD_NAMES)
				.fieldSetMapper(new BeanWrapperFieldSetMapper<MatchInputData>() {
					{
						setTargetType(MatchInputData.class);
					}
				}).build();
	}

	@Bean
	public MatchProcesssor processor() {
		return new MatchProcesssor();
	}

	@Bean
	public JdbcBatchItemWriter<Match> writer(DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<Match>()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
				.sql("INSERT INTO match (id, city, match_Date, player_Of_Match, venue, team1, team2, toss_Winner, toss_Decision, match_Winner, result, result_Margin, eliminator, method, umpire1, umpire2) "
						+ "VALUES (:id, :city, :matchDate, :playerOfMatch, :venue, :team1, :team2, :tossWinner, :tossDecision, :matchWinner, :result, :resultMargin, :eliminator, :method, :umpire1, :umpire2)")
				.dataSource(dataSource).build();
	}

	@Bean
	public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
		return jobBuilderFactory
				.get("importUserJob")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(step1)
				.end()
				.build();
	}

	@Bean
	public Step step1(JdbcBatchItemWriter<Match> writer) {
		return stepBuilderFactory
				.get("step1")
				.<MatchInputData, Match>chunk(10)
				.reader(reader())
				.processor(processor())
				.writer(writer)
				.build();
	}
}
