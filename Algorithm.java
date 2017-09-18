package dataStructure;
import java.util.ArrayList;
import java.util.Scanner;

public class Algorithm {

	/**
	 * @param args
	 */
	ArrayList<Edge> EdgeGroup1 = new ArrayList<Edge>(); // ����prim�㷨����ӱ߼���˳��
	ArrayList<Edge> EdgeGroup2 = new ArrayList<Edge>();// ����kruskal�㷨����ӱ߼���˳��
	static ArrayList<Edge> EdgeGroupExtra = new ArrayList<Edge>();// ��������

	public void Input() {
		// TODO Auto-generated method stub
		System.out.println("������ͼ�Ķ������:");
		Scanner scan = new Scanner(System.in);
		int v_num = scan.nextInt();
		System.out.println("������¼��ıߵĸ�����");
		int v_edge = scan.nextInt();
		Graph graph = new Graph(v_num);

		for (int i = 0; i < v_edge; i++) {
			System.out.println("�밴��\"����a,����b,Ȩ��\"�ĸ�ʽ¼���:");
			int x = scan.nextInt();
			int y = scan.nextInt();
			int w = scan.nextInt();

			Edge e = new Edge(x, y, w);
			graph.add(e);
			System.out.println("��¼��" + x + "��������" + y + "=" + w);

		}
		System.out.println("��¼���ͼΪ��");

	}

	public boolean Kruskal(Graph g) {

		Graph base = g;
		Graph graph = new Graph(g.vertix_number);// �洢��С����
		ArrayList<Integer> selected_vertix = new ArrayList<Integer>();
		int selected_edge = 0;
		int base_edge_num = base.edge_number;

		// �����б߶����ż�һ��..
		for (int i = 0; i < base_edge_num; i++) {
			Edge min = base.getMin(); // �õ���Щ������С�ı�
			// System.out.println("��С��Ϊ" + min.x + "����" + min.y);

			// ����¼���ıߵĲ�����·,�е��������Ϊ�ڴ����㷨����
			// ִ��kruskal����ǰ��ִ��һ��Prim�㷨����������е�Ӧ����ӵı�
			// ����ߺ�С�����ֲ�ѡ��֤���ǻ�·
			if (!hasCheck(EdgeGroupExtra, min)) {
				// System.out.println("�����л�·,����");
				base.delete(min.x, min.y);// �ӵ�ͼ��ɾ��������

			} else {

				graph.add(min);// ����������
				EdgeGroup2.add(min);
				base.delete(min.x, min.y);// ��ͼ��ɾ��������
				// System.out.println("���Լ���");
				if (!selected_vertix.contains(new Integer(min.x))) {

					selected_vertix.add(new Integer(min.x));// ����ͼ�ĵ㼯�м�����ѡ��

				}
				if (!selected_vertix.contains(new Integer(min.y))) {

					selected_vertix.add(new Integer(min.y));// ����ͼ�ĵ㼯�м�����ѡ��

				}
				selected_edge++;
			}

			if (selected_edge == base.vertix_number - 1)
				// ����n�������ͼ��һֱ�޻�·������бߣ�����ܱ�֤��ӵ�n-1���ߣ���Ϊ��
			{
				break;
			}
		}

		if (selected_edge == base.vertix_number - 1)// ����������С������
		{
			// System.out.println("�ɹ�,��С������Ϊ��");
			// graph.output();
			return true;
		} else {
			// System.out.println("�޷�����");
			return false;
		}

	}

	public boolean Prim(Graph g) {
		Graph base = g;
		Graph graph = new Graph(g.vertix_number);// �洢��С������
		ArrayList<Integer> selected_vertix = new ArrayList<Integer>();
		int selected_edge_num = 1;
		int selected_vertix_num = 2;// ��ʼʱ��ѡһ���ߣ�������1���ߣ���������
		int base_edge_num = base.edge_number;

		// ��һ�Σ��Ƚ���С�߼�����
		Edge min = base.getMin();
		graph.add(min);
		EdgeGroup1.add(min);
		selected_vertix.add(new Integer(min.x));// ����ͼ�ĵ㼯�м�����ѡ��
		selected_vertix.add(new Integer(min.y));// ����ͼ�ĵ㼯�м�����ѡ��
		base.delete(min.x, min.y);// ԭͼɾ����С��
		// System.out.println("���ȼ���" + min.x + "����" + min.y);

		ArrayList<Edge> to_choose_edge = new ArrayList<Edge>();// ��ѡ�ߺϼ�
		to_choose_edge
				.addAll(base.getEdgeByVertix((int) selected_vertix.get(0))); // ��ʼʱ������̱ߵ�һ������������ڽӱ���Ϊ��ѡ��

		int new_add = (int) selected_vertix.get(1); // ��ʼʱ����̱ߵ���һ��������Ϊ���¼ӵĵ�
		int before_add = 0;// �����ж��Ƿ����µĽ����룬��ֹ�ظ������...

		for (int i = 0; i < base_edge_num; i++) {

			// ��ѡ�߼��м���������������ڱ�
			if (new_add != before_add)// �ж�new_add��ֵ�Ƿ���� ���Ƿ����½ڵ����
				to_choose_edge.addAll(base.getEdgeByVertix(new_add));
			if (to_choose_edge.isEmpty())
				break;

			Edge choose = getMin(to_choose_edge);// ��ȡ�ڽӱ��е���С��
			// System.out.println("������С��" + choose.x + "����" + choose.y);
			before_add = new_add;

			if (selected_vertix.contains(new Integer(choose.y))
					&& selected_vertix.contains(new Integer(choose.x))) // ����¼���ıߵĲ�����·
			{
				// System.out.println("�����л�·,����");
				base.delete(choose.x, choose.y);// �ӵ�ͼ��ɾ��������
				to_choose_edge.remove(choose);// ��ѡ����ɾ��������

			} else {

				graph.add(choose);// ����������
				EdgeGroup1.add(choose);
				base.delete(choose.x, choose.y);// ��ͼ��ɾ��������
				to_choose_edge.remove(choose);// ��ѡ����ɾ��������
				// System.out.println("���Լ���");
				if (!selected_vertix.contains(new Integer(choose.x))) {
					selected_vertix.add(new Integer(choose.x));// ����ͼ�ĵ㼯�м�����ѡ��
					new_add = choose.x;
				}
				if (!selected_vertix.contains(new Integer(choose.y))) {
					selected_vertix.add(new Integer(choose.y));// ����ͼ�ĵ㼯�м�����ѡ��
					new_add = choose.y;
				}

				selected_edge_num++;
			}
			if (selected_edge_num == base.vertix_number - 1)// ����n�������ͼ��һֱ�޻�·������бߣ�����ܱ�֤��ӵ�n-1���ߣ���Ϊ��
			{
				break;
			}
		}

		EdgeGroupExtra = EdgeGroup1;

		if (selected_edge_num == base.vertix_number - 1)// ����������С������
		{
			// System.out.println("�ɹ�,��С������Ϊ��");
			// graph.output();
			return true;
		} else {
			// System.out.println("�޷�����");
			return false;
		}

	}
	
	//���·��dijkstra�㷨������v0���������������·��
	public int[] Dijkstra(Graph g,int v0){
		int n=g.vertix_number;
		int[] dist=new int[n];
		int[] prev=new int[n];
		boolean[] visited=new boolean[n];
		
		//��ʼ��
		for(int i=0;i<n;i++){
			dist[i]=g.Matrix[v0][i];
			visited[i]=false;
			//ֱ������µ�����ɵ���ǳ�����
			if(i!=v0&&dist[i]<g.noEdge)
				prev[i]=v0;
			else
				prev[i]=-1;//��ֱ��·��
		}
		
		////��ʼʱԴ��v0��visited������ʾv0 ��v0�����·���Ѿ��ҵ�
		visited[v0]=true;
		
		//����һ������ת�����������
		int minDist;
		int v=0;
		for(int i=1;i<n;i++){
			minDist=g.noEdge;
			for(int j=0;j<n;j++)
				if((!visited[j])&&dist[j]<g.noEdge){
					v=j;
					minDist=dist[j];
				}
			visited[v]=true;
			
			//��v0����v��������·��Ϊmin. �ٶ���v0��v������vֱ��������㣬���µ�ǰ���һ�����ɵ㼰����*/ 
			for(int j=0;j<n;j++){
				if((!visited[j])&&dist[j]<g.noEdge){
					dist[j]=minDist+g.Matrix[v][j];
					prev[j]=v;
					visited[j]=true;
				}
			}
		}
		return dist;
	}
	
	//���·��floyd�㷨���������������̾���
	public int[][] Floyd(Graph g){
		int n=g.vertix_number;
		int[][] d=new int[n][n];//�����i��j����С·��ֵ
		
		//��ʼ��
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++){
				d[i][j]=g.Matrix[i][j];
			}

		for(int k = 0; k < n; k++){  
            for(int i=0; i < n; i++ ){  
                for(int j=0; j < n; j++){
                	if(d[i][k]>=Integer.MAX_VALUE||d[k][j]>=Integer.MAX_VALUE)
                		continue;
                    if(d[i][j]>d[i][k]+d[k][j]){  
                        d[i][j]=d[i][k]+d[k][j];  
                    }  
                }  
            }  
        }  
		
		return d;
	}

	// ������������Ȩֵ
	public int countWeight() {
		int weight = 0;

		if (!EdgeGroup1.isEmpty())
			for (Edge e : EdgeGroup1) {
				weight += e.w;
			}
		else
			for (Edge e : EdgeGroup2) {
				weight += e.w;
			}

		return weight;

	}

	public Edge getMin(ArrayList<Edge> egroup) {
		int length = egroup.size();
		Edge min_edge = egroup.get(0);// Ĭ�ϵ�һ����Ϊ��С��
		for (int i = 1; i < length; i++) {
			if (egroup.get(i).w < min_edge.w) {
				min_edge = egroup.get(i);
			}
		}
		return min_edge;

	}

	boolean hasCheck(ArrayList<Edge> egroup, Edge e) {
		// ��������дһ���жϱ߼�����û�бߵķ���
		// ��Ϊ��������ͼ��x��y�ߵ�Ҳ��ͬһ����
		for (Edge edge : egroup) {
			if (edge.x == e.x && edge.y == e.y && edge.w == e.w)
				return true;
			else if (edge.x == e.y && edge.y == e.x && edge.w == e.w)
				return true;
		}
		return false;
	}

}
