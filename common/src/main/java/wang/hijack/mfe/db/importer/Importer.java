package wang.hijack.mfe.db.importer;

/**
 * @author Jack
 */
public interface Importer extends Comparable<Importer> {
    /**
     * 导入数据
     */
    void importData();

    /**
     * 执行顺序
     *
     * @return 顺序id
     */
    int order();

    /**
     * order小的排在前面
     *
     * @param o/
     * @return /
     */
    @Override
    default int compareTo(Importer o) {
        return order() - o.order();
    }
}
