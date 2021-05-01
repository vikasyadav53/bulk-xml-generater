package com.vikas.springboot_batch.config;

import java.io.IOException;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.ItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.vikas.springboot_batch.schemas.Employee;
import com.vikas.springboot_batch.schemas.ExamResult;

@Configuration
public class SpringBatchConfig {

	@Autowired
	private JobBuilderFactory jobs;

	@Autowired
	private StepBuilderFactory steps;

	@Value("input/record.csv")
	private Resource inputCsv;

	@Value("file:xml/output.xml")
	private Resource outputXml;

	@Autowired
	private DataSource dataSource;

	//private static final String QUERY_INSERT_STUDENT = "INSERT INTO employee(name) values (:name)";
	private static final String QUERY_INSERT_STUDENT = "insert into EXAM_RESULT(STUDENT_NAME, DOB, PERCENTAGE) values (?, ?, ?)";

	@Bean
	public Job footballJob() {
		return jobs.get("footballJob").start(step1()).build();
	}

	@Bean
	public Step step1() {
		return this.steps.get("step1").<String, String>chunk(10).reader(itemReader()).processor(getItemProcessor()).writer(itemWriter()).build();
	}
	
	@Bean
	public ItemReader itemReader() {
	    Resource[] resources = null;
	    ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();   
	    try {
	    	resources = patternResolver.getResources("file:C:/Users/Vikas Yadav/xmls/test*.xml");
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    System.out.println(resources);
	    MultiResourceItemReader<ExamResult> reader = new MultiResourceItemReader<>();
	    reader.setResources(resources);
	    reader.setDelegate(getXmlItemReader());
	    return reader;
	}
	
	@Bean
	public StaxEventItemReader<ExamResult> getXmlItemReader() {
		StaxEventItemReader<ExamResult> reader = new StaxEventItemReader<ExamResult>();
		reader.setName("itemReader");
		//reader.setResource(new FileSystemResource("C:\\Users\\Vikas Yadav\\xmls\\test.xml"));
		//reader.setResource(new FileSystemResource("C:\\Users\\Vikas Yadav\\xmls\\test.xml"));
		reader.setStrict(true);
		reader.setFragmentRootElementName("ExamResult");
		reader.setUnmarshaller(employeeMarshaller());
		return reader;
	}

	@Bean
	public Jaxb2Marshaller employeeMarshaller() {
		Jaxb2Marshaller employeeMarshal = new Jaxb2Marshaller();
		employeeMarshal.setClassesToBeBound(ExamResult.class);
		return employeeMarshal;

	}

	@Bean
	public ItemWriter itemWriter() {
		JdbcBatchItemWriter<ExamResult> databaseItemWriter = new JdbcBatchItemWriter<>();
		databaseItemWriter.setDataSource(dataSource);
		//databaseItemWriter.setJdbcTemplate(getJdbcTemplate());

		databaseItemWriter.setSql(QUERY_INSERT_STUDENT);

		ItemSqlParameterSourceProvider<Employee> paramProvider = new BeanPropertyItemSqlParameterSourceProvider<>();
		//databaseItemWriter.setItemSqlParameterSourceProvider(paramProvider);
		databaseItemWriter.setItemPreparedStatementSetter(getItemPreparedStatementSetter());

		return databaseItemWriter;
	}
	
	@Bean
	public ItemPreparedStatementSetter<ExamResult> getItemPreparedStatementSetter() {
		return new ExamResultItemPreparedStatementSetter();
	}

	@Bean
	NamedParameterJdbcTemplate getJdbcTemplate() {
		return new NamedParameterJdbcTemplate(dataSource);
	}
	
	@Bean
	public ItemProcessor<ExamResult, ExamResult> getItemProcessor(){
		return new ExamResultItemProcessor();
	}
	
	@Bean
	public JobExecutionListener getExamJobExecutionListener() {
		return new ExamResultJobListener();
	}
}
