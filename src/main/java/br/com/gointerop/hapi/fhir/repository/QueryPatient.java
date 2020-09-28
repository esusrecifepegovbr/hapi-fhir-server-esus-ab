package br.com.gointerop.hapi.fhir.repository;

public final class QueryPatient extends Query {
	private static IQuery instance;
	
	public static IQuery getInstance() {
		if(QueryPatient.instance == null) {
			QueryPatient.instance = new QueryPatient();
		}
		
		return QueryPatient.instance;
	}

	@Override
	public String readById(Long id) {
		return "select * from tb_cidadao where co_seq_cidadao = " + id.toString();
	}

}
