{
    'name': 'NoMoreLaps Ads Management',
    'version': '1.0',
    'category': 'Sales',
    'summary': 'Gestión de espacios publicitarios y campañas para la App NoMoreLaps',
    'description': """
        Módulo para la asignatura SGE.
        Permite gestionar anuncios, vincularlos a clientes del CRM y generar informes de ventas.
    """,
    'author': 'Nicolás Expósito Hernández',
    'depends': ['base', 'crm', 'sale_management'],
    'data': [
        'security/ir.model.access.csv',
        'data/ir_cron_data.xml',
        'views/ad_campaign_views.xml',
        'reports/ad_report_template.xml',
    ],
    'installable': True,
    'application': True,
}
